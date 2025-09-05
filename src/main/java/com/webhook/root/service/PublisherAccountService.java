package com.webhook.root.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;

@Service
public class PublisherAccountService {
	private final PublisherAccountRepository publisherRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public PublisherAccountService(PublisherAccountRepository publisherAccountRepository) {
		this.publisherRepository = publisherAccountRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public PublisherAccount addPublisher(PublisherAccount publisher) throws Exception {
		// check if username already exists
		if (publisherRepository.existsByUsername(publisher.getUsername())) {
			throw new Exception("Username already exists");
		}

		// encrypt password
		publisher.setPassword(passwordEncoder.encode(publisher.getPassword()));

		// save new publisher
		return publisherRepository.save(publisher);
	}

	public List<PublisherAccount> getAllAccounts() {
		return publisherRepository.findAll();
	}

	public PublisherAccount findById(UUID id) {
		return publisherRepository.findById(id)
				.orElseThrow(
					() -> new RuntimeException("User not found")
				);
	}

	public PublisherAccount findByUsername(String username) {
		return publisherRepository.findByUsername(username);
	}

	public void deleteById(UUID id) {
		publisherRepository.deleteById(id);
	}
	
}
