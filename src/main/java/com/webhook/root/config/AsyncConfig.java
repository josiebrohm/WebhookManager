package com.webhook.root.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);      // 4 threads minimum
        executor.setMaxPoolSize(16);       // up to 8 threads
        executor.setQueueCapacity(20);    // queue up to 20 jobs
        executor.setThreadNamePrefix("webhook-async-");
        executor.initialize();
        return executor;
    }
}
