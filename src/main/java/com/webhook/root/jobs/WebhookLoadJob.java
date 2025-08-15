package com.webhook.root.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webhook.root.model.Webhook;
import com.webhook.root.repository.WebhookRepository;

@Component
public class WebhookLoadJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(WebhookLoadJob.class);

    @Autowired
    private WebhookRepository webhookRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            List<Webhook> webhooks = webhookRepository.findAll();
            logger.info("Loaded {} webhook events from database", webhooks.size());
            
            // Log each webhook (optional)
            for (Webhook webhook : webhooks) {
                logger.info("Webhook ID: {}, Details: {}", webhook.getRequestId(), webhook);
            }
            
        } catch (Exception e) {
            logger.error("Error loading webhook events", e);
            throw new JobExecutionException(e);
        }
    }
}