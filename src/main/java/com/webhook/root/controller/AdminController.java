package com.webhook.root.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.attempt.SendAttemptService;
import com.webhook.root.endpoint.EndpointService;
import com.webhook.root.message.WebhookMessageService;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.SendAttempt;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.publisher.PublisherAccountService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private final PublisherAccountService publisherService;
	private final EndpointService endpointService;
	private final WebhookMessageService webhookService;
	private final SendAttemptService sendService;

	public AdminController(PublisherAccountService publisherService, EndpointService endpointService, WebhookMessageService webhookService, SendAttemptService sendService) {
		this.publisherService = publisherService;
		this.endpointService = endpointService;
		this.webhookService = webhookService;
		this.sendService = sendService;
	}

	@GetMapping("/accounts")
	public List<PublisherAccount> getAllAccounts() {
		return publisherService.getAllAccounts();
	}

	@GetMapping("/endpoints")
	public List<Endpoint> getAllEndpoints() {
		return endpointService.getAllEndpoints();
	}

	@GetMapping("/webhooks")
	public List<WebhookMessage> getAllWebhooks() {
		return webhookService.getAllWebhookMessages();
	}

	@GetMapping("/sends")
	public List<SendAttempt> getAllSendAttempts() {
		return sendService.getAllSendAttempts();
	}

	
}
