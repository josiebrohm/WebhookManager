package com.webhook.root.model;

public class WebhookMessage {
    public String payload;
    public int attemptCount;
    
    public WebhookMessage() {}
    public WebhookMessage(String payload, int attemptCount) {
        this.payload = payload;
        this.attemptCount = attemptCount;
    }
}