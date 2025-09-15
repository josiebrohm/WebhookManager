package com.webhook.root.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.DltHandler;

import com.webhook.root.message.WebhookProcessor;
import com.webhook.root.model.WebhookMessage;

@Service
public class KafkaConsumer {

	private final WebhookProcessor webhookProcessor;

	public KafkaConsumer(WebhookProcessor webhookProcessor) {
		this.webhookProcessor = webhookProcessor;
	}

	// @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 1000, multiplier = 1.2)) // Experiment with this for shorter feedback when local testing
	@RetryableTopic(attempts = "10", backoff = @Backoff(delay = 2000, multiplier = 2)) // [2s, 4s, 8s, 16s, 32s, 64s, 128s(~2 mins), ~4mins, ~8mins]  etc
    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(WebhookMessage webhookMessage) {
        System.out.println("Received webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());

		try {
			boolean shouldRetry = webhookProcessor.processWebhook(webhookMessage, 0);

        	System.out.println("shouldRetry: " + shouldRetry);
			
			if (shouldRetry)
				throw new RetryException("Got 5xx error, scheduling retry...");

		} catch (Exception e) {
			System.err.println("🚨 Webhook processing failed!");
			throw e;
		}
    }

	@DltHandler
	public void processMessage(WebhookMessage message) {
		System.err.println("Failed too many times, sending message: " + message.toString() + " to the DLQ.");
		// alertService.sendAlert("🚨 WebhookMessage send to DLQ")
		// failedWebhookRepository.save(message); // Should we add something like this, a specific DLQ sql table to make dlq investigation and replays straightforward?
	}
}
