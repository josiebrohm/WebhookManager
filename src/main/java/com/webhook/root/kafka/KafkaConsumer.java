package com.webhook.root.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.webhook.root.message.WebhookProcessor;
import com.webhook.root.model.WebhookMessage;

@Service
public class KafkaConsumer {

	private final WebhookProcessor webhookProcessor;

	public KafkaConsumer(WebhookProcessor webhookProcessor) {
		this.webhookProcessor = webhookProcessor;
	}

    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(WebhookMessage webhookMessage) {
        System.out.println("Received webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());

		try {
			boolean shouldRetry = webhookProcessor.processWebhook(webhookMessage, 0);

        	System.out.println("shouldRetry: " + shouldRetry);
			
			// if (shouldRetry)
			// 	throw retry exception

		} catch (Exception e) {
			System.err.println("Failed to process webhook: " + e.getMessage());
        	throw e;
		}
    }
}
