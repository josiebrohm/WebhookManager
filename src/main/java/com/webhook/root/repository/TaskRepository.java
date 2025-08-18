package com.webhook.root.repository;

import com.webhook.root.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t from Task t WHERE t.status = :status AND t.retryDate <= :now")
    List<Task> findReadyTasks(String status, Instant now);
}
