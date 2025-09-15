package com.webhook.root.controller;

import com.webhook.root.message.WebhookMessageReceiver;
import com.webhook.root.message.WebhookMessageRequest;
import com.webhook.root.message.WebhookMessageService;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.EndpointRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookMessageController {

    private final WebhookMessageService webhookMessageService;
	private final WebhookMessageReceiver webhookMessageReceiver;
	private final EndpointRepository endpointRepository;

    public WebhookMessageController(WebhookMessageService webhookMessageService, WebhookMessageReceiver webhookMessageReceiver, EndpointRepository endpointRepository) {
        this.webhookMessageService = webhookMessageService;
		this.webhookMessageReceiver = webhookMessageReceiver;
		this.endpointRepository = endpointRepository;
    }

    @GetMapping("/webhooks")
    public List<WebhookMessage> getAllWebhookMessages() {
        return this.webhookMessageService.getAllWebhookMessages();
    }

    @PostMapping("/webhooks")
    public WebhookMessage createWebhookMessage(@RequestBody WebhookMessageRequest webhookMessageRequest) {
        return this.webhookMessageReceiver.receiveWebhookMessage(webhookMessageRequest);
    }

	@PostMapping("/{endpoint_id}/message")
	public WebhookMessage sendWebhookMessage(@PathVariable UUID endpoint_id, @RequestBody WebhookMessageRequest messageRequest) throws Exception {
		// check if endpoint exists and is healthy
		if (!endpointRepository.existsById(endpoint_id)) throw new Exception("Endpoint does not exist");

		Endpoint endpoint = endpointRepository.findById(endpoint_id).get();
		if (!endpoint.getEnabled()) throw new Exception("Endpoint not enabled");

		return this.webhookMessageReceiver.receiveWebhookMessage(messageRequest);
	}
}
