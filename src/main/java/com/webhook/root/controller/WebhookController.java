package com.webhook.root.controller;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.SimpleProducer;
import com.webhook.root.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebhookController {

    private final WebhookService webhookService;

    @Autowired
    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Autowired
    private SimpleProducer producer;
    
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

    @GetMapping("/send")
    public String sendMesssage(@RequestParam String msg) {
        producer.send(msg);
        return "Message sent!";
    }
}
