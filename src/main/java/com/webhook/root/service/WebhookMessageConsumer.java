package com.webhook.root.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;

@Service
public class WebhookMessageConsumer {

	private final WebhookMessageSender webhookMessageSender;

	public WebhookMessageConsumer(WebhookMessageSender webhookMessageSender) {
		this.webhookMessageSender = webhookMessageSender;
	}

    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(WebhookMessage webhookMessage) {
        System.out.println("Received webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());
		webhookMessageSender.sendWebhookMessage(webhookMessage);
    }
}
