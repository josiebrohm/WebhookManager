package com.webhook.root.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.PublisherAccount;

@Repository
public interface PublisherAccountRepository extends JpaRepository<PublisherAccount, UUID> {
	PublisherAccount findByUsername(String username);
	boolean existsByUsername(String username);
	
}
