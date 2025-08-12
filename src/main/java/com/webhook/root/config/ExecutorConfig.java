package com.webhook.root.config;

import com.webhook.root.model.TaskEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.PriorityBlockingQueue;

@Configuration
public class ExecutorConfig {

    /**
     * Construction and configuration of thread pool for background task execution.
     *
     * @return configured ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setThreadNamePrefix("WebhookWorker-");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();
        return taskExecutor;
    }

    public PriorityBlockingQueue<TaskEntity> taskQueue() {
        return new PriorityBlockingQueue<>();
    }


}
