package com.webhook.root.workers;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webhook.root.model.WebhookJob;
import com.webhook.root.repository.WebhookJobRepository;
import com.webhook.root.service.WebhookProcessor;

// Replace with your actual repository/service for fetching events
@Component
public class WebhookEventScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WebhookEventScheduler.class);

    @Autowired
    private WebhookJobRepository repo;

    @Autowired
    private WebhookProcessor processor;

    @Scheduled(fixedRate = 2000)
    public void logWebhookEvents() {
        List<WebhookJob> readyJobs = repo.findByStateAndRetryAtLessThanEqual(WebhookJob.State.PENDING, LocalDateTime.now());

        if (readyJobs.isEmpty()) {
            System.out.println("No ready jobs at " + LocalDateTime.now());
        } else {
            System.out.println("Ready jobs at " + LocalDateTime.now() + ":");
            
            for (WebhookJob job : readyJobs) {
                int claimed = repo.claimJob(job.getJobId());

                if (claimed == 1) {
                    processor.process(job);
                } else {
                    // Job already claimed by another thread/process
                    System.out.println("Job " + job.getJobId() + " was already claimed.");
                }

                System.out.printf(
                "  [id=%d] payload='%s' attempt=%d retryAt=%s%n",
                    job.getJobId(), 
                    job.getPayload(), 
                    job.getAttempt(), 
                    job.getRetryAt());

                System.out.print("processing job:");

                processor.process(job);
            }
        }
    }
}