package com.webhook.root.controller;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.service.WebhookMessageReceiver;
import com.webhook.root.service.WebhookMessageService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookMessageController {

    private final WebhookMessageService webhookMessageService;
	private final WebhookMessageReceiver webhookMessageReceiver;

    public WebhookMessageController(WebhookMessageService webhookMessageService, WebhookMessageReceiver webhookMessageReceiver) {
        this.webhookMessageService = webhookMessageService;
		this.webhookMessageReceiver = webhookMessageReceiver;
    }

    @GetMapping
    public String welcome() {
        return "Welcome! How'd you GET here?";
    }

    @GetMapping("/webhooks")
    public List<WebhookMessage> getAllWebhookMessages() {
        return this.webhookMessageService.getAllWebhookMessages();
    }

    @PostMapping("/webhooks")
    public WebhookMessage addWebhook(@RequestBody WebhookMessage webhook) {
        return this.webhookMessageReceiver.receiveWebhook(webhook);
    }
}
