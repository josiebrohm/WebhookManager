package com.webhook.root.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "send_attempts")
public class SendAttempt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(name = "webhook_message_id") // TODO: look into how to properly annotate foreign keys - ManyToOne, JoinColumn etc
	private UUID webhookMessageId;

	@Column(name = "status")
	private String status;

	@Column(name = "retry_count")
	private int retryCount;

	@Column(name = "max_retries")
	private int maxRetries;

	@Column(name = "scheduled_for")
	private Instant scheduledFor;

	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "delivered_at")
	private Instant deliveredAt;

	@Column(name = "failed_at")
	private Instant failedAt;

	public SendAttempt(UUID webhookMessageId, String status, int retryCount, int maxRetries, Instant scheduledFor) {
		this.webhookMessageId = webhookMessageId;
		this.status = status;
		this.retryCount = retryCount;
		this.maxRetries = maxRetries;
		this.scheduledFor = scheduledFor;
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getWebhookMessageId() {
		return webhookMessageId;
	}

	public void setWebhookMessageId(UUID webhookMessageId) {
		this.webhookMessageId = webhookMessageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public Instant getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(Instant scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Instant deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public Instant getFailedAt() {
		return failedAt;
	}

	public void setFailedAt(Instant failedAt) {
		this.failedAt = failedAt;
	}

}
