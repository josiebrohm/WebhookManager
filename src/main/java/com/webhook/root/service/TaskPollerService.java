package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;
import com.webhook.root.model.TaskStatus;
import com.webhook.root.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskPollerService {

    private final TaskRepository taskRepository;
    private final TaskQueueService taskQueueService;

    public TaskPollerService(TaskRepository taskRepository, TaskQueueService taskQueueService) {
        this.taskRepository = taskRepository;
        this.taskQueueService = taskQueueService;
    }

    @Scheduled(fixedDelay = 10)
    public void pollDatabaseForTasks() {
        // fetch tasks ready to be executed
        int batchSize = 50;
        List<TaskEntity> readyTasks = taskRepository.findReadyTasks(TaskStatus.PENDING, Instant.now(), PageRequest.of(0, batchSize));

        // enqueue to in-mem queue
        for (TaskEntity task : readyTasks) {
            boolean queued = taskQueueService.enqueue(task);

            if (queued) {
                // update status in DB to QUEUED
                task.setTaskStatus(TaskStatus.QUEUED);
                taskRepository.save(task);
            } else {
                // deal with full queue

                break;
            }
        }
    }
}
