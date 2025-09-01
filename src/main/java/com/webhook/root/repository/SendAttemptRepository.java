package com.webhook.root.repository;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.SendAttempt;

import jakarta.transaction.Transactional;

@Repository
public interface SendAttemptRepository extends JpaRepository<SendAttempt, UUID> {
	
    @Modifying
    @Transactional
    // @Query("""
    //     UPDATE SendAttempt sa SET
    //     SET sa.status = 'RETRYING',
    //         sa.retryCount = :retryCount,
    //         sa.scheduledFor = :scheduledFor
    //     WHERE sa.id = :id
    //     """)
    @Query("UPDATE SendAttempt sa SET sa.retryCount = :retryCount, sa.scheduledFor = :scheduledFor WHERE sa.id = :id")
    int updateAttempt(
        @Param("id") UUID id,
        @Param("retryCount") int retryCount,
        @Param("scheduledFor") Instant scheduledFor
    );

    // @Modifying
    // @Transactional
    // @Query("UPDATE SendAttempt sa SET sa.status = :status, sa.retryCount = :retryCount WHERE sa.id = :id")
    // int updateStatusAndRetryCount(@Param("id") UUID id, @Param("status") String status, @Param("retryCount") int retryCount);

    @Modifying
    @Transactional
    @Query("UPDATE SendAttempt sa SET sa.status = 'DELIVERED', sa.deliveredAt = CURRENT_TIMESTAMP WHERE sa.id = :id")
    int markAsDelivered(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE SendAttempt sa SET sa.status = 'DEAD_LETTER', sa.failedAt = CURRENT_TIMESTAMP WHERE sa.id = :id")
    int markAsDead(@Param("id") UUID id);
}
