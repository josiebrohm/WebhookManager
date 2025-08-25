package com.webhook.root.controller;

import com.webhook.root.service.KafkaProducerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class KafkaMessageController {

    private final KafkaProducerService producerService;

    public KafkaMessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage(message);
        return "Sent: " + message;
    }
}
