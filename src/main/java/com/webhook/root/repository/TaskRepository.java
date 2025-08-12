package com.webhook.root.repository;

import com.webhook.root.model.TaskEntity;
import com.webhook.root.model.TaskStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t from TaskEntity t where t.taskStatus = :status " +
            "AND t.retryDate <= :now " +
            "ORDER BY t.retryDate ASC")
    List<TaskEntity> findReadyTasks(
            @Param("status") TaskStatus status,
            @Param("now") Instant now,
            PageRequest pageRequest);
}
