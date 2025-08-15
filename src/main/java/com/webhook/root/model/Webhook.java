package com.webhook.root.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "webhook_events")
public class Webhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "payload")
    private String payload;

    @Column(name = "customer_id")
    private Long customerId;

    public Webhook(String targetUrl, String sourceUrl, String payload, Long customerId) {
        this.targetUrl = targetUrl;
        this.sourceUrl = sourceUrl;
        this.payload = payload;
        this.customerId = customerId;
    }

    public Webhook() {

    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "requestId=" + requestId +
                ", targetUrl='" + targetUrl + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", payload='" + payload + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
