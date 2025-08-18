package com.webhook.root.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payload;
    private String status;
    private Instant retryDate;
    private int attemptNumber;

    public Task(String payload) {
        this.payload = payload;
        this.status = "PENDING";
        this.retryDate = Instant.now();
        this.attemptNumber = 0;
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getRetryDate() {
        return retryDate;
    }

    public void setRetryDate(Instant retryDate) {
        this.retryDate = retryDate;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
}
