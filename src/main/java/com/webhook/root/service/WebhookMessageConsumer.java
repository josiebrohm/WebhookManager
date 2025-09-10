package com.webhook.root.service;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WebhookMessageConsumer {

	private final WebhookMessageSender webhookMessageSender;

	public WebhookMessageConsumer(WebhookMessageSender webhookMessageSender) {
		this.webhookMessageSender = webhookMessageSender;
	}

    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UUID webhookMessageId) {
        System.out.println("Received webhook message id: " + webhookMessageId);
		webhookMessageSender.sendWebhookMessage(webhookMessageId);
    }
}
