package com.webhook.root.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.endpoint.EndpointService;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;
import com.webhook.root.security.SecurityUtil;

@RestController
@RequestMapping("/endpoints")
public class EndpointController {
	
	private final EndpointService endpointService;
	private final PublisherAccountRepository publisherRepository;

	public EndpointController(EndpointService endpointService, PublisherAccountRepository publisherRepository) {
		this.endpointService = endpointService;
		this.publisherRepository = publisherRepository;
	}

	@GetMapping
	public List<Endpoint> getAllEndpoints() {
		// get logged in user
		PublisherAccount publisher = SecurityUtil.getCurrentPublisher(publisherRepository);

		return endpointService.getEndpointsByPublisher(publisher);
	}

	@PostMapping
	public Endpoint addEndpoint(@RequestBody Endpoint endpoint) {
		// get logged in user
		PublisherAccount publisher = SecurityUtil.getCurrentPublisher(publisherRepository);

		// associate endpoint with creator
		endpoint.setPublisherAccount(publisher);

		return endpointService.addEndpoint(endpoint);
	}
}
