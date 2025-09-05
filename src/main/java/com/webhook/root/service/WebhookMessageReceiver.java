package com.webhook.root.service;

import org.springframework.stereotype.Service;

import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.model.WebhookMessageRequest;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageReceiver {

	private final WebhookMessageRepository webhookMessageRepository;
	private final WebhookMessageProducer producerService;
	private final PublisherAccountService accountService;
	private final EndpointService endpointService;

	public WebhookMessageReceiver(WebhookMessageRepository webhookMessageRepository, 
								WebhookMessageProducer producerService, 
								PublisherAccountService accountService, 
								EndpointService endpointService) {
        this.webhookMessageRepository = webhookMessageRepository;
		this.producerService = producerService;
		this.endpointService = endpointService;
		this.accountService = accountService;
    }

	public WebhookMessage receiveWebhookMessage(WebhookMessageRequest request) {
		Endpoint endpoint = endpointService.findById(request.getEndpointId()).get();
		PublisherAccount publisherAccount = accountService.findById(request.getPublisherAccountId());

		WebhookMessage webhookMessage = new WebhookMessage(endpoint, publisherAccount, request.getHeaders(), request.getPayload(), request.getEventType());
		// save to DB
		webhookMessageRepository.save(webhookMessage);

		// add to kafka queue
        producerService.sendMessage("main-topic", webhookMessage);

        return webhookMessage;
	}
}
