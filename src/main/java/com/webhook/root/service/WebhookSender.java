package com.webhook.root.service;

import org.springframework.stereotype.Component;

import com.webhook.root.model.WebhookJob;

@Component
public class WebhookSender {
    public boolean trySend(WebhookJob job) {
        // Fail 90% of the time
        return Math.random() < 0.1;

        // always fail for now
        // return false;
    }
}