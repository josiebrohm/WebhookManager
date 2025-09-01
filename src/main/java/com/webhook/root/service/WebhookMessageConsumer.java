package com.webhook.root.service;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookMessage;
import com.webhook.root.service.Retry.RetryDecider;
import com.webhook.root.service.Retry.WebhookRetryStatus;
import com.webhook.root.service.storage.BatchProcessor;
import com.webhook.root.service.storage.SendAttemptBatchProcessor.SendAttemptUpdate;

@Service
public class WebhookMessageConsumer {

	private final WebhookMessageSender webhookMessageSender;
	private final RetryDecider retryDecider;
	private final BatchProcessor<SendAttemptUpdate> batchProcessor;
	private static final Logger log = LoggerFactory.getLogger(WebhookMessageConsumer.class);

	public WebhookMessageConsumer(WebhookMessageSender webhookMessageSender, RetryDecider retryDecider,
			BatchProcessor<SendAttemptUpdate> batchProcessor) {
		this.webhookMessageSender = webhookMessageSender;
		this.retryDecider = retryDecider;
		this.batchProcessor = batchProcessor;
	}

    // @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    // public void listen(WebhookMessage webhookMessage) {
    //     System.out.println("Received webhook message: " + webhookMessage.getId() + "\n" + webhookMessage.getEventType());
	// 	webhookMessageSender.sendWebhookMessage(webhookMessage);
    // }

	// main-topic, main-topic-1000, main-topic-2000, main-topic-16000

	@RetryableTopic(
        attempts = "10",  // MAGIC NUMBERS R BAD, MAKE THIS CONFIGURABLE // 1 original + 13 retry = 12 total attempts
        backoff = @Backoff(
			delay = 1000,		// start with 1s
			multiplier = 2.0	// then [2s, 4s, 8s, 16s, 32s, 64s, 128s, 256s/4m, 8.4m, 17m, 34m, 1hr]
		),
        include = {RetryException.class} // Only retry on this exception
    )
    @KafkaListener(topics = "main-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(
		WebhookMessage webhookMessage,
		@Header(value = "retry_topic-attempts", required = false) byte[] retryAttemptsBytes,
		ConsumerRecord<String, WebhookMessage> record) {

		Integer retryAttempt = determineRetryAttempt(retryAttemptsBytes);

		// int currentAttempt = retryAttempt != null ? retryAttempt : 1;
		boolean isLastAttempt = retryAttempt >= 13;

		log.info("Processing webhook {} - attempt {}/10{}",
			webhookMessage.getId(),
			retryAttempt,
			isLastAttempt ? " (FINAL)" : "");

		HttpStatusCode response = webhookMessageSender.tryFakeSend(webhookMessage);

		// boolean shouldRetry = retryDecider.shouldRetry(response); // return a more sophisticated decision than a yes/no
		WebhookRetryStatus status = retryDecider.determineStatus(retryAttempt, isLastAttempt, response);

		// updateDB;
		batchProcessor.add(new SendAttemptUpdate(status, webhookMessage.getId(), retryAttempt, 
		    isLastAttempt ? Instant.now() : calculateNextAttempt(retryAttempt)));

		if (status == WebhookRetryStatus.RETRYING) {
			throw new RetryException("Got 5xx error, retrying...");
		}
	}

	private Integer determineRetryAttempt(byte[] headerInBytes) {
		if (headerInBytes != null && headerInBytes.length >= 4) {
			try {
				return ByteBuffer.wrap(headerInBytes).getInt();
			} catch (Exception e) {
				log.warn("Could not decode retry_topic-attempts header: {}", e.getMessage());
			}
		}
		return 1;
	}

	private Instant calculateNextAttempt(int retryAttempt) {
		long delayMs = (long) (1000 * Math.pow(2, retryAttempt));
		return Instant.now().plusMillis(delayMs);
	}
}
