package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;
import com.webhook.root.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manages underlying PriorityBlockingQueue.
 */
@Service
public interface TaskQueueService {
    /**
     * Add a task to the in-memory queue.
     *
     * @param task to be added
     * @return true if successfully enqueued, false otherwise
     */
    boolean enqueue(TaskEntity task);

    /**
     * Retrieve and remove the next task in the queue
     *
     * @return TaskEntity if successful OR Optional.empty() if queue is empty
     */
    Optional<TaskEntity> dequeue();
}
