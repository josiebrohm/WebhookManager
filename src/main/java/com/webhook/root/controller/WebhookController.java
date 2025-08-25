package com.webhook.root.controller;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.WebhookReceiver;
import com.webhook.root.service.WebhookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookController {

    private final WebhookService webhookService;
	private final WebhookReceiver webhookReceiver;

    public WebhookController(WebhookService webhookService, WebhookReceiver webhookReceiver) {
        this.webhookService = webhookService;
		this.webhookReceiver = webhookReceiver;
    }

    @GetMapping
    public String welcome() {
        return "Welcome! How'd you GET here?";
    }

    @GetMapping("/webhooks")
    public Iterable<Webhook> getAllWebhooks() {
        return this.webhookService.getAllWebhooks();
    }

    @PostMapping("/webhooks")
    public Webhook addWebhook(@RequestBody Webhook webhook) {
        return this.webhookReceiver.receiveWebhook(webhook);
    }
}
