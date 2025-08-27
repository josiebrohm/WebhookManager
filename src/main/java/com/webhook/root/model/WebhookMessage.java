package com.webhook.root.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "webhook_messages")
public class WebhookMessage {

	@Id
	@Column(columnDefinition = "uuid")
	private UUID id = UUID.randomUUID();

	@ManyToOne // one endpoint for many potential webhooks?
	@JoinColumn(name = "endpoint_id", referencedColumnName = "id")
	private Endpoint endpoint;

	@ManyToOne // also potentially many webhooks published by one publisher
	@JoinColumn(name = "publisher_account_id", referencedColumnName = "id")
	private PublisherAccount publisherAccount;

	// string for now - need to figure out best way to implement json in java, might have to make a custom class
	@Column(name = "headers")
	private String headers; 

	@Column(name = "payload")
	private String payload;

	@Column(name = "event_type")
	private String eventType;

	@Column(name = "created_at")
	private Instant createdAt;

	public WebhookMessage(Endpoint endpoint, PublisherAccount publisherAccount, String headers, String payload, String eventType) {
		this.endpoint = endpoint;
		this.publisherAccount = publisherAccount;
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

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public PublisherAccount getPublisherAccount() {
		return publisherAccount;
	}

	public void setPublisherAccount(PublisherAccount publisherAccount) {
		this.publisherAccount = publisherAccount;
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
