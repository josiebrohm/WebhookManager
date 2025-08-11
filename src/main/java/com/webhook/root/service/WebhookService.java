package com.webhook.root.service;

import com.webhook.root.model.Webhook;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebhookService {
    private final RestTemplate restTemplate = new RestTemplate();

    private final List<Webhook> webhooks = new ArrayList<>();

    public List<Webhook> getAllWebhooks() {
        return webhooks;
    }

    public void receiveWebhook(Webhook webhook) {
        webhooks.add(webhook);
        forwardWebhook(webhook);
    }

    public void forwardWebhook(Webhook webhook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(webhook.getPayload(), headers);

        try {
            String response = restTemplate.postForObject(webhook.getTargetUrl(), request, String.class);
            System.out.println("Webhook forwarding successful");
            System.out.println(response);
        } catch (RestClientException e){
            System.out.println("Error forwarding webhook: ");
            System.out.println(e.getMessage());
        }
    }
}
