package com.webhook.root.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;

@Service
public class WebhookMessageConsumer {

	private final WebhookMessageSender webhookMessageSender;

	public WebhookMessageConsumer(WebhookMessageSender webhookMessageSender) {
		this.webhookMessageSender = webhookMessageSender;
	}

    // @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    // public void listen(WebhookMessage webhookMessage) {
    //     System.out.println("Received webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());
	// 	webhookMessageSender.sendWebhookMessage(webhookMessage);
    // }

	@RetryableTopic(
        attempts = "12",  // 1 original + 11 retry = 12 total attempts
        backoff = @Backoff(
			delay = 1000,		// start with 1s
			multiplier = 2.0	// then [2s, 4s, 8s, 16s, 32s, 64s, 128s, 256s (4.2minutes), 8.4m, 17m, 34m, 1hr]
			// delay = 5000, 		// Start with 5s...
			// multiplier = 2.0	// then [ 10s, 20s, 40s, 1m 20s, 2m 40s, 5m 20s, 10m 40s, 21m 20s ]
		),
        include = {RetryException.class} // Only retry on this exception
    )
	@KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(WebhookMessage webhookMessage) {
		System.out.println("WebhookMessageConsumer: Processing webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());

		int statusCode = webhookMessageSender.trySend(webhookMessage);

		if (statusCode == 500) {
			System.err.print("Got 5xx error, will try again later...");
			throw new RetryException("Got 5xx error, retrying...");
		}

		System.out.println("Success: " + webhookMessage.getId());
	}
}
