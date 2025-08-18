package com.webhook.root.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.webhook.root.model.WebhookJob;
import com.webhook.root.repository.WebhookJobRepository;

@Service
public class WebhookProcessor {

    @Autowired
    private WebhookJobRepository repo;

    @Autowired
    private WebhookSender sender;

    @Async("taskExecutor")
    public void process(WebhookJob job) {
        System.out.println(
            "Processing job " 
            + job.getJobId() 
            + " on thread " 
            + Thread.currentThread().getName());

        // For now always fail the job, successes will be the easy part
        boolean successful = sender.trySend(job);
        
        if (successful) {
            System.out.println("Succeeded!" + job.getJobId() + " on thread " + Thread.currentThread().getName());
            handleSuccess(job);
        }
        else {
            System.out.println("FAILED!" + job.getJobId() + " on thread " + Thread.currentThread().getName());
            handleFailure(job);
        }
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