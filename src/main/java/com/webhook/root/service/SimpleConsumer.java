package com.webhook.root.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webhook.root.model.WebhookMessage;

@Service
public class SimpleConsumer {

    private static final String DLQ_TOPIC = "test-topic-dlq";

    private static final int MAX_ATTEMPTS = 10;

    @Autowired
    private SimpleProducer producer;

    @Autowired
    private RedisRetryService retryService;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received: " + message);
        WebhookMessage webhookMsg = parseMessage(message);
        System.out.println("Received: " + message + "with attemptCount: " + webhookMsg.attemptCount);

        boolean success = sendWebhook(message);

        if (!success) {
            handleFailure(webhookMsg);
        } else {
            // handleSuccess
            System.out.println("Sent " + message + " successfully.");
        }
    }

    private void handleFailure(WebhookMessage webhookMsg) {
        if (webhookMsg.attemptCount >= MAX_ATTEMPTS) { // DLQ after 10
            // handle failure
            System.out.print("Failed more than times, sending to DLQ...");
            producer.sendToTopic(DLQ_TOPIC, webhookMsg.payload);
            return;
        }

        webhookMsg.attemptCount++;
        String retryMessage = serialize(webhookMsg);

        if (webhookMsg.attemptCount <= 3) {
            // retry immediately on 2, 3
            producer.send(retryMessage);
        } else {
            // redis retry
            retryService.scheduleRetry(retryMessage, calculuateRetry(webhookMsg.attemptCount));
        }
        
    }

    private int calculuateRetry(int attempt) {
        // 2^(n-4)
        return (int) Math.pow(2, attempt - 2);  // 3: 4s, 4: 8s, 5: 16s, 6: 32s
    }

    // private void handleFailureOld(WebhookMessage webhookMsg) {
    //     if (webhookMsg.attemptCount >= 10) {

    //         return;
    //     }

    //     webhookMsg.attemptCount++;
    //     String retryMessage = new ObjectMapper().writeValueAsString(webhookMsg);

    //     if (webhookMsg.attemptCount <= 2) { // immediate retry on 0,1

    //         try {
    //             System.out.println("failed, retrying immediately... attempt " + webhookMsg.attemptCount);
    //             producer.send(retryMessage);
    //         } catch (Exception e) {
    //             System.err.println("Failed to serialize retry message: " + e.getMessage());
    //         }
    //     } else if (webhookMsg.attemptCount <= 5) { // Redis retry for { attempt 2: 4s, attempt 3: 8s, attempt 4: 16s, attempt 5: 32s }
    //         // Schedule with Redis for medium delay
    //         System.out.println("Scheduling medium delayed retry with Redis... " + webhookMsg.attemptCount);

    //         webhookMsg.attemptCount++;
    //         String retryMessage = new ObjectMapper().writeValueAsString(webhookMsg);

    //         retryService.scheduleRetry(retryMessage, 10);
    //     } else if (webhookMsg.attemptCount < 10) {
    //         // use DB scheduling
    //     }
    //     else {
    //         // Failed more than 10 times, send to DLQ
    //         System.out.print("Failed more than times, sending to DLQ...");
    //         producer.sendToTopic(DLQ_TOPIC, webhookMsg.payload);
    //     }
    // }


    // @KafkaListener(topics = "test-topic", groupId = "test-group")
    // public void listenOld(String message) {
    //     // IMPLEMENT DATABASE BACKUP

    //     System.out.println("Received: " + message);
    //     var attempt = parseMessage(message).attemptCount;
    //     System.out.println("Received: " + message + "with attemptCount: " + attempt);

    //     // fake webhook call
    //     boolean success = sendWebhook(message);

    //     if (!success) {
    //         // retry
    //         WebhookMessage webhookMsg = parseMessage(message);
    //         if (webhookMsg.attemptCount <= 2) { // immediate retry on 0,1,2
    //             webhookMsg.attemptCount++;
    //             try {
    //                 String retryMessage = new ObjectMapper().writeValueAsString(webhookMsg);
    //                 System.out.println("failed, retrying immediately... attempt " + webhookMsg.attemptCount);
    //                 producer.send(retryMessage);
    //             } catch (Exception e) {
    //                 System.err.println("Failed to serialize retry message: " + e.getMessage());
    //             }
    //         } else {
    //             // Send to Redis for 10s delay
    //             System.out.println("Scheduling 10s delayed retry");
    //             webhookMsg.attemptCount++;
    //             retryService.scheduleRetry(message, 10);
    //         }
    //         // else if (webhookMsg.attemptCount <= 6) { // attempts numbers 2, 3, 4, 5, 6
    //         //     webhookMsg.attemptCount++;

    //         //     try {
    //         //         String delayedMessage = new ObjectMapper().writeValueAsString(webhookMsg);
    //         //         System.out.println("failed multiple times, retrying on delayed queue " + webhookMsg.attemptCount);
    //         //         retryService.scheduleRetry(delayedMessage, 10);
    //         //     } catch (Exception e) {
    //         //         System.err.println("Failed to serialize retry message: " + e.getMessage());
    //         //         // should we do something else in this case?
    //         //     }
    //         // } else {
    //         //     System.out.println("failed more than 6 times, giving up!");
    //         // }
    //     }
    // }

    @Scheduled(fixedDelay = 2000) // Check every 2 seconds
    public void processDelayedRetries() {
        System.out.println("Redis heartbeat, no delayed messages yet... ");
        List<String> readyMessages = retryService.popReadyMessages();
        
        for (String message : readyMessages) {
            System.out.println("Processing delayed message: " + message);
            processRetryMessage(message);
        }
    }

    private void processRetryMessage(String message) {
        // IMPLEMENT DATABASE BACKUP
        
        WebhookMessage webhookMsg = parseMessage(message);
        webhookMsg.attemptCount++;
        boolean success = sendWebhook(webhookMsg.payload);

        if (success) {
            System.out.println("Delayed retry succeeded for: " + webhookMsg.payload);
        } else if (webhookMsg.attemptCount <= 6) {
                System.out.println("Scheduling 10s delayed retry, attempt number: " + webhookMsg.attemptCount);
                retryService.scheduleRetry(message, 10);
        } else {
            System.out.println("10s retry failed, sending to DLQ");
            try {
                String dlqMessage = new ObjectMapper().writeValueAsString(webhookMsg);
                producer.sendToTopic(DLQ_TOPIC, dlqMessage);
            } catch (Exception e) {
                System.err.println("Failed to send to DLQ: " + e.getMessage());
            }
        }
    }

    private String serialize(WebhookMessage webhookMsg) {
        try {
            return new ObjectMapper().writeValueAsString(webhookMsg);
        } catch (Exception e) {
            System.err.println("Failed to serialize webhook message: " + e.getMessage());
            throw new RuntimeException("Cannot serialize webhook message for retry", e);
        }
    }

    private WebhookMessage parseMessage(String message) {
        try {
            return new ObjectMapper().readValue(message, WebhookMessage.class);
        } catch (Exception e) {
            return new WebhookMessage(message, 0);
        }
    }

    private boolean sendWebhook(String message) {
        // // always fail
        // return false;
        // 80% failure rate for testing
        return Math.random() > 0.95;
    }
}
