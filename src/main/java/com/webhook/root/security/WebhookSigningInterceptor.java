package com.webhook.root.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

public class WebhookSigningInterceptor implements ClientHttpRequestInterceptor {

	private final String secret;

	public WebhookSigningInterceptor(String secret) {
		this.secret = secret;
	}

	@Override
	@NonNull
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		try {
			String jsonPayload = new String(body, StandardCharsets.UTF_8);

			long timestamp = System.currentTimeMillis() / 1000L;
			String signature = SecurityUtil.signWebhookPayload(jsonPayload, timestamp, secret);

			request.getHeaders().set("X-Signature", signature);
			request.getHeaders().set("X-Timestamp", String.valueOf(timestamp));
		} catch (Exception e) {
			throw new RuntimeException("Failed to sign webhook request", e);
		}

		return execution.execute(request, body);
	}
	
}
