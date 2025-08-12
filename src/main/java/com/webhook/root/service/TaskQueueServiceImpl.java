package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;

import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskQueueServiceImpl implements TaskQueueService {

    private final PriorityBlockingQueue<TaskEntity> taskQueue;

    public TaskQueueServiceImpl(PriorityBlockingQueue<TaskEntity> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public boolean enqueue(TaskEntity task) {
        return taskQueue.offer(task);
    }

    @Override
    public Optional<TaskEntity> dequeue() {
        return Optional.ofNullable(taskQueue.poll());
    }
}
