package com.webhook.root.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;

@Service
public class WebhookMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WebhookMessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, WebhookMessage webhookMessage) {
        kafkaTemplate.send(topic, webhookMessage);
    }

}
