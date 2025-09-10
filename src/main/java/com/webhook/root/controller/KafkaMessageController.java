package com.webhook.root.controller;

import com.webhook.root.service.WebhookMessageProducer;

import java.util.UUID;

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
    public String sendMessage(@PathVariable String topic, @RequestBody UUID webhookMessageId) {
        producerService.sendMessage(topic, webhookMessageId);
        return "Sent: " + webhookMessageId.toString();
    }
}
