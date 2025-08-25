package com.webhook.root.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "webook_attempts")
public class WebhookAttempt {
    @Id
    private String id;
    
    @Column(name = "webhook_url")
    private String webhookUrl;
    
    private String payload;
    
    @Column(name = "attempt_count")
    private Integer attemptCount = 0;
    
    @Column(name = "max_attempts")
    private Integer maxAttempts = 10;
    
    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;
    
    private String status = "PENDING";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public WebhookAttempt() {}

    public WebhookAttempt(String id, String webhookUrl, String payload) {
        this.id = id;
        this.webhookUrl = webhookUrl;
        this.payload = payload;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getWebhookUrl() { return webhookUrl; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
    
}
