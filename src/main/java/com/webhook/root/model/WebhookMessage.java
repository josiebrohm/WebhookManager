package com.webhook.root.model;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonType;

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

	@ManyToOne(optional = true) // one endpoint for many potential webhooks?
	@JoinColumn(name = "endpoint_id", referencedColumnName = "id")
	private Endpoint endpoint;

	@ManyToOne(optional = true) // also potentially many webhooks published by one publisher
	@JoinColumn(name = "publisher_account_id", referencedColumnName = "id")
	private PublisherAccount publisherAccount;

	// string for now - need to figure out best way to implement json in java, might have to make a custom class
	@Type(JsonType.class)
	@Column(name = "headers", columnDefinition = "json")
	private Map<String, Object> headers; 

	@Type(JsonType.class)
	@Column(name = "payload", columnDefinition = "json")
	private Map<String, Object> payload;

	@Column(name = "event_type")
	@JsonProperty("event_type")
	private String eventType;

	@Column(name = "created_at")
	private Instant createdAt = Instant.now();

	public WebhookMessage(Endpoint endpoint, PublisherAccount publisherAccount, Map<String, Object> headers, Map<String, Object> payload, String eventType) {
		this.endpoint = endpoint;
		this.publisherAccount = publisherAccount;
		this.headers = headers;
		this.payload = payload;
		this.eventType = eventType;
	}

	public WebhookMessage() {

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

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
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
