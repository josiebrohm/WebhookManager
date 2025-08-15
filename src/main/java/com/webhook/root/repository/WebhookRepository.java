package com.webhook.root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.Webhook;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, Long> {

}
