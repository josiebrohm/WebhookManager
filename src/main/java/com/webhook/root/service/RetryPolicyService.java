package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;
import com.webhook.root.model.TaskStatus;
import com.webhook.root.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RetryPolicyService {
    private final TaskRepository taskRepository;
    private final int MAX_ATTEMPTS = 10;

    public RetryPolicyService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void handleTaskFailure(TaskEntity task) {
        int attempts = task.getAttemptNumber() + 1;
        task.setAttemptNumber(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            // something must be broken, stop retrying
            task.setTaskStatus(TaskStatus.FAILED);
            task.setRetryDate(null);
        } else {
            // put back in DB with pending status
            task.setTaskStatus(TaskStatus.PENDING);
            Instant nextRetry = calculateNextRetry(attempts);

            if (nextRetry != null) {
                task.setRetryDate(nextRetry);
            }
        }
    }

    private Instant calculateNextRetry(int attempts) {
        return switch (attempts) {
            case 1 -> // 1 second
                    Instant.now().plusSeconds(1);
            case 2 -> // 1 minute
                    Instant.now().plusSeconds(60);
            case 3 -> // 5 minutes
                    Instant.now().plusSeconds(5 * 60);
            case 4 -> // 30 minutes
                    Instant.now().plusSeconds(30 * 60);
            case 5 -> // 1 hour
                    Instant.now().plusSeconds(60 * 60);
            case 6 -> // 2 hours
                    Instant.now().plusSeconds(2 * 60 * 60);
            case 7 -> // 5 hours
                    Instant.now().plusSeconds(5 * 60 * 60);
            case 8 -> // 12 hours
                    Instant.now().plusSeconds(12 * 60 * 60);
            case 9 -> // 24 hours
                    Instant.now().plusSeconds(24 * 60 * 60);
            case 10 -> // 48 hours
                    Instant.now().plusSeconds(48 * 60 * 60);
            default -> null;
        };
    }
}
