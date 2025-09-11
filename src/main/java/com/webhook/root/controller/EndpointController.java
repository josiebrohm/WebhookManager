package com.webhook.root.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.endpoint.EndpointService;
import com.webhook.root.model.Endpoint;

@RestController
@RequestMapping("/endpoints")
public class EndpointController {
	
	private final EndpointService endpointService;

	public EndpointController(EndpointService endpointService) {
		this.endpointService = endpointService;
	}

	@GetMapping
	public List<Endpoint> getAllEndpoints() {
		return this.endpointService.getAllEndpoints();
	}

	@PostMapping
	public Endpoint addEndpoint(@RequestBody Endpoint endpoint) {
		endpointService.addEndpoint(endpoint);
		return endpoint;
	}
}
