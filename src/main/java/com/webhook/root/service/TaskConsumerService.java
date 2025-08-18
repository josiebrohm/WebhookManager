package com.webhook.root.service;

import com.webhook.root.model.Task;
import com.webhook.root.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class TaskConsumerService {

    private final TaskRepository taskRepository;

    private final BlockingQueue<Task> taskQueue;

    private final TaskRetryService retryService;

    @Autowired
    public TaskConsumerService(TaskRepository taskRepository, BlockingQueue<Task> taskQueue, TaskRetryService retryService) {
        this.taskRepository = taskRepository;
        this.taskQueue = taskQueue;
        this.retryService = retryService;
    }

    @Async
    public void processTask() {
        while (true) {
            try {
                Task task = taskQueue.take();

                // perform task logic
                System.out.println("Processing task: " + task.getId());

                task.setStatus("COMPLETED");
                taskRepository.save(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                // update task status, potentially schedule retry
            }
        }
    }
}
