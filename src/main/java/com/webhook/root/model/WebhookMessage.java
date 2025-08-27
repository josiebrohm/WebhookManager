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
@Table(name = "webhook_messages")
public class WebhookMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(name = "endpoint_id")
	private UUID endpointId;

	@Column(name = "publisher_account_id")
	private UUID publisherAccountId;

	// string for now - need to figure out best way to implement json in java, might have to make a custom class
	@Column(name = "headers")
	private String headers; 

	@Column(name = "payload")
	private String payload;

	@Column(name = "event_type")
	private String eventType;

	@Column(name = "created_at")
	private Instant createdAt;

	public WebhookMessage(UUID endpointId, UUID publisherAccountId, String headers, String payload, String eventType) {
		this.endpointId = endpointId;
		this.publisherAccountId = publisherAccountId;
		this.headers = headers;
		this.payload = payload;
		this.eventType = eventType;
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(UUID endpointId) {
		this.endpointId = endpointId;
	}

	public UUID getPublisherAccountId() {
		return publisherAccountId;
	}

	public void setPublisherAccountId(UUID publisherAccountId) {
		this.publisherAccountId = publisherAccountId;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
