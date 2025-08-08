package com.webhook.root.service;

import com.webhook.root.model.Webhook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
        System.out.println(webhook.getId());
        forwardWebhook(webhook);
    }

    public void forwardWebhook(Webhook webhook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(webhook.getPayload(), headers);

        String response = restTemplate.postForObject(webhook.getTargetUrl(), request, String.class);
        System.out.println(response);
    }
}
