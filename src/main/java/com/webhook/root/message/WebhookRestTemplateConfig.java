package com.webhook.root.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.webhook.root.security.WebhookSigningInterceptor;

@Configuration
public class WebhookRestTemplateConfig {
	
	@Bean
	public RestTemplate webhookRestTemplate(@Value("${webhook.secret}") String secret) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(
			List.of(new WebhookSigningInterceptor(secret))
		);
		return restTemplate;
	}
}
