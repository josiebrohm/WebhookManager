package com.webhook.root.service.storage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatusCode;

import com.webhook.root.repository.SendAttemptRepository;
import com.webhook.root.service.Retry.WebhookRetryStatus;


public class SendAttemptBatchProcessor {
    // public record SendAttemptUpdate(WebhookRetryStatus status,
    //     HttpStatusCode response, UUID webhookId) {}

    public record SendAttemptUpdate(WebhookRetryStatus status,
        UUID webhookId, int retryNumber, Instant scheduledFor) {}

    private final int MAX_BATCH_SIZE = 1000;
    private final int PROCESSOR_TICK_HEARTBEAT = 5000; // 5s

    private final List<SendAttemptUpdate> bucket = new ArrayList<>();

    private final SendAttemptRepository repo;

    public SendAttemptBatchProcessor(SendAttemptRepository repo) {
        this.repo = repo;
    }

    public void update(SendAttemptUpdate update) {
        // update status
    }

    public void add(SendAttemptUpdate update) {

        if (update.status == WebhookRetryStatus.SCHEDULED) {
            // update retry
        } else if (update.status == WebhookRetryStatus.DELIVERED) {
            // set as successful
        } else {
            // set as failed or dlq

        }
    }

    // public void add(SendAttemptUpdate update) {
    //     // add to bucket
    //     bucket.add(update);
    //     // if bucket is full -> write to DB
    //     if (bucket.size() >= MAX_BATCH_SIZE)
    //         repo.write() ?
    // }
    
    // every 5 seconds
    // -> write to DB
}