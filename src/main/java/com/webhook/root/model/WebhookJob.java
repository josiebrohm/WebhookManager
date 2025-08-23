package com.webhook.root.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "webhook_jobs")
public class WebhookJob {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "payload")
    private String payload;

    @Column(name = "customer_id")
    private Long customerId;

    public enum State {
        PENDING, DEAD, DELIVERED, PROCESSING
    }

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "retry_at")
    private LocalDateTime retryAt;

    @Column(name = "attempt")
    private Integer attempt;

    public Long getJobId() {
        return jobId;
    }

    public State getState() {
        return state;
    }

    public String getPayload() {
        return payload;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getRetryAt() {
        return retryAt;
    }

    public void setRetryAt(LocalDateTime retryAt) {
        this.retryAt = retryAt;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
}