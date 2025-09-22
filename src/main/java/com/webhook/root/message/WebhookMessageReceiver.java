package com.webhook.root.message;

import org.springframework.stereotype.Service;

import com.webhook.root.endpoint.EndpointService;
import com.webhook.root.kafka.KafkaProducer;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.publisher.PublisherAccountService;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageReceiver {

	private final WebhookMessageRepository webhookMessageRepository;
	private final KafkaProducer producerService;

	public WebhookMessageReceiver(WebhookMessageRepository webhookMessageRepository, 
								KafkaProducer producerService, 
								PublisherAccountService accountService, 
								EndpointService endpointService) {
        this.webhookMessageRepository = webhookMessageRepository;
		this.producerService = producerService;
    }

	public WebhookMessage receiveWebhookMessage(WebhookMessage webhookMessage) {

		// save to DB
		webhookMessageRepository.save(webhookMessage);

		// add to kafka queue
        producerService.sendMessage("main-topic", webhookMessage);

        return webhookMessage;
	}
}
