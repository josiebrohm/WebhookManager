package com.webhook.root.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.webhook.root.model.Endpoint;
import com.webhook.root.repository.EndpointRepository;

@Service
public class EndpointService {
	private final EndpointRepository endpointRepository;

	public EndpointService(EndpointRepository endpointRepository) {
		this.endpointRepository = endpointRepository;
	}

	public Endpoint addEndpoint(Endpoint endpoint) {
		endpointRepository.save(endpoint);
		return endpoint;
	}

	public List<Endpoint> getAllEndpoints() {
		return endpointRepository.findAll();
	}

	public Optional<Endpoint> findById(UUID id) {
		return endpointRepository.findById(id);
	}

	public void deleteById(UUID id) {
		endpointRepository.deleteById(id);
	}
}
