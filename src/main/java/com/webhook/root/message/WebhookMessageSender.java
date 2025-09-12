package com.webhook.root.message;

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

    public HttpStatusCode trySendWebhookMessage(WebhookMessage webhookMessage) {
        Double r = Math.random();
        if (r < 0.2) {
            return HttpStatus.OK;
        } else if (r < 0.4) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
