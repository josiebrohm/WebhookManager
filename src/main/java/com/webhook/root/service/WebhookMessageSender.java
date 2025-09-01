package com.webhook.root.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.webhook.root.model.WebhookMessage;

@Service
public class WebhookMessageSender {

	private final RestTemplate restTemplate = new RestTemplate();

	public void sendWebhookMessage(WebhookMessage webhookMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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

    public HttpStatusCode tryFakeSend(WebhookMessage webhookMessage) {
        double r = Math.random();
        if (r < 0.1) {             // 10% return success
            return HttpStatus.OK;
        } else if (r < 0.2) {     // 10% return client error
            return HttpStatus.BAD_REQUEST;
        } else {                    // 80% return server error
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public int fakeSend(WebhookMessage webhookMessage) {
        double r = Math.random();
        // 90% chance of failing
        if (r > 0.9) {
            return 200;
        } else {
            return 500;
        }
    }
	
}
