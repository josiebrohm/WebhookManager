package com.webhook.root.controller;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.model.WebhookMessageRequest;
import com.webhook.root.service.WebhookMessageReceiver;
import com.webhook.root.service.WebhookMessageService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class WebhookMessageController {

    private final WebhookMessageService webhookMessageService;
	private final WebhookMessageReceiver webhookMessageReceiver;

    public WebhookMessageController(WebhookMessageService webhookMessageService, WebhookMessageReceiver webhookMessageReceiver) {
        this.webhookMessageService = webhookMessageService;
		this.webhookMessageReceiver = webhookMessageReceiver;
    }

    @GetMapping
    public List<WebhookMessage> getAllWebhookMessages() {
        return this.webhookMessageService.getAllWebhookMessages();
    }

    @PostMapping
    public WebhookMessage createWebhookMessage(@RequestBody WebhookMessageRequest webhookMessageRequest) {
        return this.webhookMessageReceiver.receiveWebhookMessage(webhookMessageRequest);
    }
}
