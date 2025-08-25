package com.webhook.root.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.webhook.root.model.Webhook;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Webhook webhook) {
        System.out.println("Received webhook: " + webhook.getRequestId() + "\n" + webhook.getPayload());
    }
}
