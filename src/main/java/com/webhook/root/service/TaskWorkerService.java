package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;
import com.webhook.root.model.TaskStatus;
import com.webhook.root.repository.TaskRepository;
import com.webhook.root.runner.TaskRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskWorkerService {

    private final TaskRepository taskRepository;
    private final TaskQueueService taskQueueService;
    private final RetryPolicyService retryPolicyService;
    private final TaskRunner taskRunner;

    public TaskWorkerService(TaskQueueService taskQueueService,
                             TaskRunner taskRunner,
                             RetryPolicyService retryPolicyService,
                             TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskQueueService = taskQueueService;
        this.retryPolicyService = retryPolicyService;
        this.taskRunner = taskRunner;
    }

    public void processTasks() {
        while (true) {

            try {
                TaskEntity task = taskQueueService.dequeue();

                boolean success = taskRunner.runTask(task);

                if (success) {
                    task.setTaskStatus(TaskStatus.COMPLETED);
                    taskRepository.save(task);
                } else {
                    retryPolicyService.handleTaskFailure(task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task processing interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Error processing task: " + e.getMessage());
            }
        }
    }
}
