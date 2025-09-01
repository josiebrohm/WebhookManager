package com.webhook.root.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// I propose we rename this to WebhookDeliveryStatus (or WebhookStatus)?
@Entity
@Table(name = "send_attempts")
public class SendAttempt {
	
	@Id
	@Column(columnDefinition = "uuid")
	private UUID id = UUID.randomUUID();

	@ManyToOne // unsure if many to one is correct, but perhaps could be many send attempts?
	@JoinColumn(name = "webhook_message_id", referencedColumnName = "id")
	private WebhookMessage webhookMessage;

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

	public SendAttempt(WebhookMessage webhookMessage, String status, int retryCount, int maxRetries, Instant scheduledFor) {
		this.webhookMessage = webhookMessage;
		this.status = status;
		this.retryCount = retryCount;
		this.maxRetries = maxRetries;
		this.scheduledFor = scheduledFor;
		this.createdAt = Instant.now();
	}

	public SendAttempt() {
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public WebhookMessage getWebhookMessage() {
		return webhookMessage;
	}

	public void setWebhookMessage(WebhookMessage webhookMessage) {
		this.webhookMessage = webhookMessage;
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
