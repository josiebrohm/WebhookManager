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
@Table(name = "endpoints")
public class Endpoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(name = "url")
	private String url;

	@Column(name = "secret")
	private String secret;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "last_success_at")
	private Instant lastSuccessAt;

	@Column(name = "last_failure_at")
	private Instant lastFailureAt;

	public Endpoint(String url, String secret, boolean enabled) {
		this.url = url;
		this.secret = secret;
		this.enabled = enabled;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Instant getLastSuccessAt() {
		return lastSuccessAt;
	}

	public void setLastSuccessAt(Instant lastSuccessAt) {
		this.lastSuccessAt = lastSuccessAt;
	}

	public Instant getLastFailureAt() {
		return lastFailureAt;
	}

	public void setLastFailureAt(Instant lastFailureAt) {
		this.lastFailureAt = lastFailureAt;
	}

	
}
