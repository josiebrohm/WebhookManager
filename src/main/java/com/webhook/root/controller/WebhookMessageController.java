package com.webhook.root.controller;

import com.webhook.root.message.WebhookMessageReceiver;
import com.webhook.root.message.WebhookMessageRequest;
import com.webhook.root.message.WebhookMessageService;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.EndpointRepository;
import com.webhook.root.repository.PublisherAccountRepository;
import com.webhook.root.security.SecurityUtil;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookMessageController {

    private final WebhookMessageService webhookMessageService;
	private final WebhookMessageReceiver webhookMessageReceiver;
	private final EndpointRepository endpointRepository;
	private final PublisherAccountRepository publisherRepository;

    public WebhookMessageController(WebhookMessageService webhookMessageService, WebhookMessageReceiver webhookMessageReceiver, EndpointRepository endpointRepository, PublisherAccountRepository publisherRepository) {
        this.webhookMessageService = webhookMessageService;
		this.webhookMessageReceiver = webhookMessageReceiver;
		this.endpointRepository = endpointRepository;
		this.publisherRepository = publisherRepository;
    }

    @GetMapping("/webhooks")
    public List<WebhookMessage> getAllWebhookMessages() {
		// get logged in user
		PublisherAccount publisher = SecurityUtil.getCurrentPublisher(publisherRepository);

        return webhookMessageService.getWebhookMessagesByPublisher(publisher);
    }

	@PostMapping("/{endpointId}/message")
	public WebhookMessage sendWebhookMessage(@PathVariable UUID endpointId, @RequestBody WebhookMessageRequest request) throws Exception {
		// get publisher from security context
		PublisherAccount publisher = SecurityUtil.getCurrentPublisher(publisherRepository);

		// find endpoint from path variable
		Endpoint endpoint = endpointRepository.findByIdAndPublisherAccount(endpointId, publisher)
				.orElseThrow(() -> new Exception("Endpoint not found"));

		if (!endpoint.getEnabled()) throw new Exception("Endpoint not enabled");

		WebhookMessage message = new WebhookMessage(
				endpoint, 
				publisher, 
				request.getHeaders(), 
				request.getPayload(), 
				request.getEventType()
		);

		return webhookMessageReceiver.receiveWebhookMessage(message);
	}
}
