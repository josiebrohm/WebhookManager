package com.webhook.root.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, WebhookMessage webhookMessage) {
        kafkaTemplate.send(topic, webhookMessage);
    }

}
