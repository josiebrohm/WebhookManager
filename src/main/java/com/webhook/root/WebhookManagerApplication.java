package com.webhook.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebhookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhookManagerApplication.class, args);
	}

}
