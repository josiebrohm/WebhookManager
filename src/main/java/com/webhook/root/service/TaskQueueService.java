package com.webhook.root.service;

import com.webhook.root.model.TaskEntity;

/**
 * Manages underlying PriorityBlockingQueue.
 */
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
     * @return TaskEntity
     */
    TaskEntity dequeue() throws InterruptedException;
}
