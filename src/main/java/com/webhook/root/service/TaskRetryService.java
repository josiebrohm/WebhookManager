package com.webhook.root.service;

import com.webhook.root.model.Task;
import com.webhook.root.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TaskRetryService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskRetryService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void handleTaskFailure(Task task) {
        if (task.getAttemptNumber() >= 10) {
            task.setStatus("FAILED");
            taskRepository.save(task);
        } else {
            task.setStatus("PENDING");
            task.setRetryDate(Instant.now().plusSeconds((long) Math.pow(2, task.getAttemptNumber())));
            taskRepository.save(task);
        }
    }
}
