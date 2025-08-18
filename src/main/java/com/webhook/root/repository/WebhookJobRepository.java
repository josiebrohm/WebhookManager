package com.webhook.root.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.WebhookJob;

@Repository
public interface WebhookJobRepository extends JpaRepository<WebhookJob, Long> {
        List<WebhookJob> findByStateAndRetryAtLessThanEqual(WebhookJob.State state, LocalDateTime retryAt);
}
