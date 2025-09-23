package com.webhook.root.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "publisher_accounts")
public class PublisherAccount {

	@Id
	@Column(columnDefinition = "uuid")
	private UUID id = UUID.randomUUID();

	@Enumerated(EnumType.STRING)
	private Role role = Role.PUBLISHER;

	@Column(name = "name")
	private String name;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "rate_limit")
	private int rateLimit;

	public PublisherAccount(String name, String username, String password, int rateLimit) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.rateLimit = rateLimit;
	}

	public PublisherAccount() {

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}
}