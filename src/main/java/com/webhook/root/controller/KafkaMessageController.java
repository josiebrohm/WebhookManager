package com.webhook.root.controller;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.service.WebhookMessageProducer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class KafkaMessageController {

    private final WebhookMessageProducer producerService;

    public KafkaMessageController(WebhookMessageProducer producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@PathVariable String topic, @RequestBody WebhookMessage webhookMessage) {
        producerService.sendMessage(topic, webhookMessage);
        return "Sent: " + webhookMessage.toString();
    }
}
