package com.webhook.root.service.Retry;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class RetryDecider {
    public boolean shouldRetry(HttpStatusCode response) {
		if (response.is2xxSuccessful()) {
			// don't retry successes
			// update DB
			System.out.println("SUCCESS: " + response.toString());
            return false;

		} 
        else if (response.is4xxClientError()) {
			// client errors and bad requests, scenarios not to retry -> DLQ
			System.err.print("CLIENT ERROR: " + response.toString() + "Got 4xx error, will not retry");
			// update DB
            return false;

        } else if (response.is5xxServerError()) {
			// server errors and retry scenarios
			System.err.print("SERVER ERROR: " + response.toString() + "Got 5xx error, will try again later...");
			// update DB
            return true;

		} else {
            System.err.print("UNEXPECTED RESPONSE: " + response.toString() + "will retry");
            return true;
        }
    }

    public WebhookRetryStatus determineStatus(int retryAttempt, boolean isLastAttempt, HttpStatusCode response) {
        if (response.is2xxSuccessful()) {
			// don't retry successes
			System.out.println("SUCCESS: " + response.toString());
            return WebhookRetryStatus.DELIVERED;

		}
        else if (response.is4xxClientError() || (isLastAttempt && response.isError())) {
			// client errors and bad requests, scenarios not to retry -> DLQ
			System.err.print("CLIENT ERROR: " + response.toString() + "Got 4xx error, will not retry");
            return WebhookRetryStatus.DEAD_LETTER;

        } else if (response.is5xxServerError() && !isLastAttempt) {
			// server errors and retry scenarios
			System.err.print("SERVER ERROR: " + response.toString() + "Got 5xx error, will try again later...");
            return WebhookRetryStatus.RETRYING;

        } else if (!isLastAttempt) {
            // unexpected status code, retrying
            return WebhookRetryStatus.RETRYING;

        } else {
            // UNEXPECTED SITUATION !!! MAKE NOISE ! How should we handle this???? 
            System.out.println("RetryDecider: UNEXPECTED SITUATION !!! resonse did not match any expected case. Sending to DLQ");
            // Probably shouldn't leave things up to optimistic defaults chance
            return WebhookRetryStatus.DEAD_LETTER;
        }
    }

    // public WebhookRetryStatus determineStatus(int retryAttempt, HttpStatusCode response) {
    //     return switch (true) {
    //         case response.is2xxSuccessful() -> WebhookRetryStatus.DELIVERED;
    //         default -> WebhookRetryStatus.SCHEDULED;
    //     };
    // }
}
