package com.webhook.root.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.WebhookAttempt;

@Repository
public interface WebhookAttemptRepository extends JpaRepository<WebhookAttempt, String> {
    
    List<WebhookAttempt> findByStatusAndNextRetryAtBefore(String status, LocalDateTime now);
    
    List<WebhookAttempt> findByStatus(String status);
    
}
