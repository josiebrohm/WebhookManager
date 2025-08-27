package com.webhook.root.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "publisher_accounts")
public class PublisherAccount {

	@Id
	@Column(columnDefinition = "uuid")
	private UUID id = UUID.randomUUID();

	@Column(name = "name")
	private String name;

	@Column(name = "api_key")
	private String apiKey;

	@Column(name = "rate_limit")
	private int rateLimit;

	public PublisherAccount(String name, String apiKey, int rateLimit) {
		this.name = name;
		this.apiKey = apiKey;
		this.rateLimit = rateLimit;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}
}