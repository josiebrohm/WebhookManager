package com.webhook.root.service;

import com.webhook.root.model.Task;
import com.webhook.root.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
public class TaskProducerService {

    private final TaskRepository taskRepository;

    private final BlockingQueue<Task> taskQueue;

    @Autowired
    public TaskProducerService(TaskRepository taskRepository, BlockingQueue<Task> taskQueue) {
        this.taskRepository = taskRepository;
        this.taskQueue = taskQueue;
    }

    @Scheduled(fixedDelay = 1000)
    public void fetchAndQueueTasks() {
        List<Task> readyTasks = taskRepository.findReadyTasks("PENDING", Instant.now());

        for (Task task : readyTasks) {
            task.setStatus("PROCESSING");
            taskRepository.save(task);
            boolean offer = taskQueue.offer(task);// add to in-mem queue
        }
    }

}
