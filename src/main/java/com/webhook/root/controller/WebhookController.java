package com.webhook.root.controller;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.WebhookReceiver;
import com.webhook.root.service.WebhookAdmin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookController {

    private final WebhookAdmin webhookAdmin;
	private final WebhookReceiver webhookReceiver;

    public WebhookController(WebhookAdmin webhookAdmin, WebhookReceiver webhookReceiver) {
        this.webhookAdmin = webhookAdmin;
		this.webhookReceiver = webhookReceiver;
    }

    @GetMapping
    public String welcome() {
        return "Welcome! How'd you GET here?";
    }

    @GetMapping("/webhooks")
    public Iterable<Webhook> getAllWebhooks() {
        return this.webhookAdmin.getAllWebhooks();
    }

    @PostMapping("/webhooks")
    public Webhook addWebhook(@RequestBody Webhook webhook) {
        return this.webhookReceiver.receiveWebhook(webhook);
    }
}
