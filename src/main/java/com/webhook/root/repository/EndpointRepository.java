package com.webhook.root.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, UUID> {
	List<Endpoint> findByPublisherAccount(PublisherAccount publisher);
	Optional<Endpoint> findByIdAndPublisherAccount(UUID id, PublisherAccount publisher);
	
}
