package com.webhook.root.controller;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.WebhookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @GetMapping
    public String welcome() {
        return "Welcome! How'd you GET here?";
    }

    @PostMapping
    public String receiveWebhook(@RequestBody Webhook webhook) {

        webhookService.receiveWebhook(webhook);
        return "Webhook received: " + webhook.getRequestBody();
    }
}
