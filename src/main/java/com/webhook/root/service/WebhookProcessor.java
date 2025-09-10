package com.webhook.root.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.webhook.root.model.SendAttempt;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.SendAttemptRepository;

@Service
class WebhookProcessor {

	private final WebhookMessageSender sender;
	private final SendAttemptRepository repo;

	WebhookProcessor(WebhookMessageSender sender, SendAttemptRepository repo) {
		this.sender = sender;
		this.repo = repo;
	}

	private boolean shouldRetry(HttpStatusCode result) {
		return result.is5xxServerError();
	}

    public boolean processWebhook(WebhookMessage webhookMessage, int attemptNumber) {
        var result = sender.trySendWebhookMessage(webhookMessage);

		SendAttempt attempt = new SendAttempt(
			webhookMessage,
			result.toString(),
			0, // TODO: when we implement retry logic 
			15,
			Instant.now() // scheduledFor, we also need to set this when we implement retry
		);

		repo.save(attempt);

		return shouldRetry(result);
    }
}