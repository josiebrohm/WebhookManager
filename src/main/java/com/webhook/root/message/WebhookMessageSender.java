package com.webhook.root.message;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.webhook.root.model.WebhookMessage;

@Service
public class WebhookMessageSender {

	private final RestTemplate webhookRestTemplate;

	public WebhookMessageSender(RestTemplate webhookRestTemplate) {
		this.webhookRestTemplate = webhookRestTemplate;
	}

	public HttpStatusCode sendWebhookMessage(WebhookMessage webhookMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> payload = new HttpEntity<>(webhookMessage.getPayload(), headers);

		ResponseEntity<String> response = webhookRestTemplate.postForEntity(webhookMessage.getEndpoint().getUrl(), payload, String.class);

        return response.getStatusCode();
    }

    public HttpStatusCode tryRealSendWithHighFakeFailureRate(WebhookMessage webhookMessage) {
        Double r = Math.random();
        if (r < 0.9) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> payload = new HttpEntity<>(webhookMessage.getPayload(), headers);

            ResponseEntity<String> response = webhookRestTemplate.postForEntity(
                webhookMessage.getEndpoint().getUrl(),
                payload,
                String.class);

            // successful
            return response.getStatusCode();
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatusCode trySendWebhookMessage(WebhookMessage webhookMessage) {
        Double r = Math.random();
        if (r < 0.05) {
            return HttpStatus.OK;
        } else if (r < 0.1) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
