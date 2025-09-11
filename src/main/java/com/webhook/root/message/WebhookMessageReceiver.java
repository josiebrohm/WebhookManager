package com.webhook.root.message;

import org.springframework.stereotype.Service;

import com.webhook.root.endpoint.EndpointService;
import com.webhook.root.kafka.KafkaProducer;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.publisher.PublisherAccountService;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageReceiver {

	private final WebhookMessageRepository webhookMessageRepository;
	private final KafkaProducer producerService;
	private final PublisherAccountService accountService;
	private final EndpointService endpointService;

	public WebhookMessageReceiver(WebhookMessageRepository webhookMessageRepository, 
								KafkaProducer producerService, 
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
