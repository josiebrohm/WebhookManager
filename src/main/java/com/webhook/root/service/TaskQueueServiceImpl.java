package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;

@Service
public class TaskQueueServiceImpl implements TaskQueueService {

    private final PriorityBlockingQueue<TaskEntity> taskQueue;

    public TaskQueueServiceImpl() {
        this.taskQueue = new PriorityBlockingQueue<>();
    }

    @Override
    public boolean enqueue(TaskEntity task) {
        return taskQueue.offer(task);
    }

    @Override
    public TaskEntity dequeue() throws InterruptedException {
        return taskQueue.take();
    }
}
