package com.webhook.root.controller;

import com.webhook.root.model.Webhook;
import com.webhook.root.service.KafkaProducerService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class KafkaMessageController {

    private final KafkaProducerService producerService;

    public KafkaMessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@PathVariable String topic, @RequestBody Webhook webhook) {
        producerService.sendMessage(topic, webhook);
        return "Sent: " + webhook.toString();
    }
}
