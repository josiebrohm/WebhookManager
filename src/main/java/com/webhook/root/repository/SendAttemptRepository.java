package com.webhook.root.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.SendAttempt;

@Repository
public interface SendAttemptRepository extends JpaRepository<SendAttempt, UUID> {
	
}
