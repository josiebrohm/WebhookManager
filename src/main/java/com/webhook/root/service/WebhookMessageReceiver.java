package com.webhook.root.service;

import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageReceiver {

	private final WebhookMessageRepository webhookMessageRepository;
	private final WebhookMessageProducer producerService;

	public WebhookMessageReceiver(WebhookMessageRepository webhookMessageRepository, WebhookMessageProducer producerService) {
        this.webhookMessageRepository = webhookMessageRepository;
		this.producerService = producerService;
    }

	public WebhookMessage receiveWebhook(WebhookMessage webhookMessage) {
		// save to DB
		webhookMessageRepository.save(webhookMessage);

		// add to kafka queue
        producerService.sendMessage("main-topic", webhookMessage);

        return webhookMessage;
	}
}
