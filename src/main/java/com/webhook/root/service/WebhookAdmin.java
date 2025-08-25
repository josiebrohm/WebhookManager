package com.webhook.root.service;

import com.webhook.root.model.Webhook;
import com.webhook.root.repository.WebhookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebhookAdmin {
    private final WebhookRepository webhookRepository;

    public WebhookAdmin(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
    }

    public List<Webhook> getAllWebhooks() {
        return webhookRepository.findAll();
    }

    public Optional<Webhook> findById(Long id) {
        return webhookRepository.findById(id);
    }

    public void deleteById(Long id) {
        webhookRepository.deleteById(id);
    }
}
