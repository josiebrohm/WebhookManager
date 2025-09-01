package com.webhook.root.service.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.webhook.root.model.SendAttempt;
import com.webhook.root.repository.SendAttemptRepository;
import com.webhook.root.service.Retry.WebhookRetryStatus;
import com.webhook.root.service.storage.SendAttemptBatchProcessor.SendAttemptUpdate;

@Service
public class BatchProcessor<T> {

    private final int MAX_BATCH_SIZE = 1000;
    private final int PROCESSOR_TICK_HEARTBEAT = 5000; // 5s

    private final List<T> bucket = new ArrayList<>();

    private final SendAttemptRepository repo;

    public BatchProcessor(SendAttemptRepository repo) {
        this.repo = repo;
    }

    public void add(SendAttemptUpdate update) {
        if (update.status() == WebhookRetryStatus.SCHEDULED) {
            // update retry
            repo.updateAttempt(update.webhookId(), update.retryNumber(), update.scheduledFor());
        } else if (update.status() == WebhookRetryStatus.DELIVERED) {
            // set as successful
            repo.markAsDelivered(update.webhookId());
        } else {
            // set as failed or dlq
            repo.markAsDead(update.webhookId());
        }
    }

    /* 
    public void add(T item) {
        // add to bucket
        bucket.add(item);
        // if bucket is full -> write to DB
        
    }
    */
    
    // every 5 seconds
    // -> write to DB

}
