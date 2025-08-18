package com.webhook.root.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async("taskExecutor")
    public void executeAsyncTask() {
        System.out.println("Execute method async: " + Thread.currentThread().getName());

        try {
            // simulate long-running task
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Task execution completed! " + Thread.currentThread().getName());
    }
}
