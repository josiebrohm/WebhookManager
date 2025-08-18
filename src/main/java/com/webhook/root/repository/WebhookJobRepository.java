package com.webhook.root.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.WebhookJob;

import jakarta.transaction.Transactional;

@Repository
public interface WebhookJobRepository extends JpaRepository<WebhookJob, Long> {
    List<WebhookJob> findByStateAndRetryAtLessThanEqual(WebhookJob.State state, LocalDateTime retryAt);

    @Transactional
    @Modifying
    @Query(
      value = "UPDATE webhook_jobs SET state = 'PROCESSING' " +
              "WHERE job_id = :id AND state = 'PENDING' ",
              // order by ascending retryAt 
      nativeQuery = true
    )
    int claimJob(Long id);
}
