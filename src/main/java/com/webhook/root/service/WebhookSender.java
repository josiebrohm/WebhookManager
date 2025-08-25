package com.webhook.root.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.webhook.root.model.Webhook;

@Service
public class WebhookSender {

	private final RestTemplate restTemplate = new RestTemplate();

	public void sendWebhook(Webhook webhook) {
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
	
}
