package com.webhook.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.WebhookService;

@RestController
@RequestMapping("/")
public class WebhookController {

    private final WebhookService webhookService;

    @Autowired
    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
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
        return this.webhookService.saveWebhook(webhook);
    }
}
