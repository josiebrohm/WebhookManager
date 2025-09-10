package com.webhook.root.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.repository.WebhookMessageRepository;

@Service
public class WebhookMessageSender {

	private final RestTemplate restTemplate = new RestTemplate();
	private final WebhookMessageRepository webhookMessageRepository;

	public WebhookMessageSender(WebhookMessageRepository webhookMessageRepository) {
		this.webhookMessageRepository = webhookMessageRepository;
	}

	public void sendWebhookMessage(UUID webhookMessageId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

		// fetch the webhook from db by id
		Optional<WebhookMessage> optional = webhookMessageRepository.findById(webhookMessageId);

		// basic error handling if no webhook matching ID is found
		if (!optional.isPresent()) {
			System.err.println("WEBHOOK NOT FOUND");
			return;
		}

		WebhookMessage webhookMessage = webhookMessageRepository.findById(webhookMessageId).get();

        HttpEntity<Map<String, Object>> payload = new HttpEntity<>(webhookMessage.getPayload(), headers);

        try {
            String response = restTemplate.postForObject(webhookMessage.getEndpoint().getUrl(), payload, String.class);
            System.out.println("Webhook forwarding successful");
            System.out.println(response);
        } catch (RestClientException e){
            System.out.println("Error forwarding webhook: ");
            System.out.println(e.getMessage());
        }
    }
	
}
