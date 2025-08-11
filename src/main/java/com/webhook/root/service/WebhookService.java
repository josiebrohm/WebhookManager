package com.webhook.root.service;

import com.webhook.root.model.Webhook;
import com.webhook.root.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class WebhookService {
    private final WebhookRepository webhookRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public WebhookService(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
    }

    public List<Webhook> getAllWebhooks() {
        return webhookRepository.findAll();
    }

    public Optional<Webhook> findById(Long id) {
        return webhookRepository.findById(id);
    }

    public Webhook saveWebhook(Webhook webhook) {
        webhookRepository.save(webhook);
        forward(webhook);
        return webhook;
    }

    public void forward(Webhook webhook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> payload = new HttpEntity<>(webhook.getPayload(), headers);

        try {
            String response = restTemplate.postForObject(webhook.getTargetUrl(), payload, String.class);
            System.out.println("Webhook forwarding successful");
            System.out.println(response);
        } catch (RestClientException e){
            System.out.println("Error forwarding webhook: ");
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(Long id) {
        webhookRepository.deleteById(id);
    }
}
