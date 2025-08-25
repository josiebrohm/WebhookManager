package com.webhook.root.service;

import org.springframework.stereotype.Service;

import com.webhook.root.model.Webhook;
import com.webhook.root.repository.WebhookRepository;

@Service
public class WebhookReceiver {

	private final WebhookRepository webhookRepository;
	private final WebhookMessageProducer producerService;

	public WebhookReceiver(WebhookRepository webhookRepository, WebhookMessageProducer producerService) {
        this.webhookRepository = webhookRepository;
		this.producerService = producerService;
    }

	public Webhook receiveWebhook(Webhook webhook) {
		// save to DB
		webhookRepository.save(webhook);

		// add to kafka queue
        producerService.sendMessage("main-topic", webhook);

        return webhook;
	}
}
