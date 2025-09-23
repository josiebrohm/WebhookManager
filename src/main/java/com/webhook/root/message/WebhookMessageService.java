package com.webhook.root.message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageService {
	private final WebhookMessageRepository webhookMessageRepository;

	public WebhookMessageService(WebhookMessageRepository webhookMessageRepository) {
		this.webhookMessageRepository = webhookMessageRepository;
	}

	public WebhookMessage addWebhookMessage(WebhookMessage webhookMessage) {
		webhookMessageRepository.save(webhookMessage);
		return webhookMessage;
	}

	public List<WebhookMessage> getAllWebhookMessages() {
		return webhookMessageRepository.findAll();
	}

	public List<WebhookMessage> getWebhookMessagesByPublisher(PublisherAccount publisher) {

	}

	public Optional<WebhookMessage> findById(UUID id) {
		return webhookMessageRepository.findById(id);
	}

	public void deleteById(UUID id) {
		webhookMessageRepository.deleteById(id);
	}
}
