package com.webhook.root.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.webhook.root.jobs.WebhookLoadJob;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail webhookLoadJobDetail() {
        return JobBuilder.newJob(WebhookLoadJob.class)
                .withIdentity("webhookLoadJob")
                .withDescription("Load webhook events from database")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger webhookLoadJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(webhookLoadJobDetail())
                .withIdentity("webhookLoadJobTrigger")
                .withDescription("Trigger for webhook load job")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
    }
}