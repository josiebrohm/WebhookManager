package com.webhook.root.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookJob;
import com.webhook.root.repository.WebhookJobRepository;

@Service
public class WebhookProcessor {

    @Autowired
    private WebhookJobRepository repo;

    @Autowired
    private WebhookSender sender;

    public void process(WebhookJob job) {
        // For now always fail the job, successes will be the easy part
        boolean successful = sender.trySend(job);
        
        if (successful)
            handleSuccess(job);
        else 
            handleFailure(job);
    }

    private void handleSuccess(WebhookJob job) {
        job.setState(WebhookJob.State.DELIVERED);
        repo.save(job);
    }

    private void handleFailure(WebhookJob job) {
        if (job.getAttempt() >= 10) {
            job.setState(WebhookJob.State.DEAD);
        } else {
            job.setAttempt(job.getAttempt() + 1);
            job.setRetryAt(LocalDateTime.now().plusMinutes(calculateRetry(job)));
        }
        // job.setState("pending");
        repo.save(job);
    }

    private long calculateRetry(WebhookJob job) {
        return (1 + (job.getAttempt() / 2));
    }
}