package com.webhook.root.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "attempt_number")
    private int attemptNumber;

    @Column(name = "retry_date")
    private Instant retryDate;

    public TaskEntity(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        this.attemptNumber = 0;
        this.retryDate = Instant.now();
    }

    public TaskEntity() {

    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Instant getRetryDate() {
        return retryDate;
    }

    public void setRetryDate(Instant retryDate) {
        this.retryDate = retryDate;
    }
}
