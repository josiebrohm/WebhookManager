package com.webhook.root.service;

import java.rmi.UnexpectedException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
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

    public HttpStatusCode trySend(WebhookMessage webhookMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        
            HttpEntity<Map<String, Object>> payload = new HttpEntity<>(webhookMessage.getPayload(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                webhookMessage.getEndpoint().getUrl(),
                // "http://localhost:8080/simulate/" + simulateSend(webhookMessage),
                payload,
                String.class);

            // successful
            return response.getStatusCode();

        } catch (HttpClientErrorException clientError) {
            // handle 4xx errors
            // do not retry
            return clientError.getStatusCode();

        } catch (HttpServerErrorException serverError) {
            // handle 5xx errors
            // schedule retry
            return serverError.getStatusCode();

        } catch (HttpStatusCodeException e) {
            return e.getStatusCode();
        }
    }

    public HttpStatusCode trySendChaotic(WebhookMessage webhookMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        
            HttpEntity<Map<String, Object>> payload = new HttpEntity<>(webhookMessage.getPayload(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/simulate/chaos",
                payload,
                String.class);

            // successful
            return response.getStatusCode();

        } catch (HttpClientErrorException clientError) {
            // handle 4xx errors
            // do not retry
            return clientError.getStatusCode();

        } catch (HttpServerErrorException serverError) {
            // handle 5xx errors
            // schedule retry
            return serverError.getStatusCode();

        } catch (HttpStatusCodeException e) {
            return e.getStatusCode();
        }
    }

    public int simulateSend(WebhookMessage webhookMessage) {
        double r = Math.random();
        if (r < 0.333) {         // 0.00 - 0.33: return success
            return 200;
        } else if (r < 0.666) {  // 0.33 - 0.66: return client error
            return 400;
        } else {                // 0.66 - 1.00: return server error
            return 500;
        }
    }

    public record WebhookResult(boolean success, int code, HttpStatusCode statusCode) {}
}
