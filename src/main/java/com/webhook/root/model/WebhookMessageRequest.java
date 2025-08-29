package com.webhook.root.model;

import java.util.Map;
import java.util.UUID;

public class WebhookMessageRequest {
	private UUID endpointId;
	private UUID publisherAccountId;
	private Map<String, Object> headers;
	private Map<String, Object> payload;
	private String eventType;

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
}
