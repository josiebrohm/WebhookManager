package com.webhook.root.controller;

import com.webhook.root.message.WebhookMessageReceiver;
import com.webhook.root.message.WebhookMessageRequest;
import com.webhook.root.message.WebhookMessageService;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.EndpointRepository;
import com.webhook.root.repository.PublisherAccountRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return this.webhookMessageService.getAllWebhookMessages();
    }

	@PostMapping("/{endpoint_id}/message")
	public WebhookMessage sendWebhookMessage(@PathVariable UUID endpoint_id, @RequestBody WebhookMessageRequest request) throws Exception {
		// check if endpoint exists and is healthy
		if (!endpointRepository.existsById(endpoint_id)) throw new Exception("Endpoint does not exist");

		// find endpoint from path variable
		Endpoint endpoint = endpointRepository.findById(endpoint_id).get();
		if (!endpoint.getEnabled()) throw new Exception("Endpoint not enabled");

		// get publisher from security context
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		PublisherAccount publisher = publisherRepository.findByUsername(username);

		WebhookMessage message = new WebhookMessage(endpoint, publisher, request.getHeaders(), request.getPayload(), request.getEventType());

		return this.webhookMessageReceiver.receiveWebhookMessage(message);
	}
}
