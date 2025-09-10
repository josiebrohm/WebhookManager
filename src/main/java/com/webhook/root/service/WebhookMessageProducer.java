package com.webhook.root.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebhookMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WebhookMessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, UUID webhookMessageId) {
        kafkaTemplate.send(topic, webhookMessageId);
    }

}
