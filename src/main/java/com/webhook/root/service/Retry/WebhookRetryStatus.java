package com.webhook.root.service.Retry;

public enum WebhookRetryStatus {
    FIRST_ATTEMPT,
    RETRYING,
    SCHEDULED,
    DELIVERED,
    DEAD_LETTER
}

// public class WebhookDeliveryStatus {
    
// }
