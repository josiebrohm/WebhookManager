package com.webhook.root.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;

@Service
public class PublisherAccountService {
	private final PublisherAccountRepository publisherAccountRepository;

	public PublisherAccountService(PublisherAccountRepository publisherAccountRepository) {
		this.publisherAccountRepository = publisherAccountRepository;
	}

	public PublisherAccount addAccount(PublisherAccount account) {
		publisherAccountRepository.save(account);
		return account;
	}

	public List<PublisherAccount> getAllAccounts() {
		return publisherAccountRepository.findAll();
	}

	public Optional<PublisherAccount> findById(UUID id) {
		return publisherAccountRepository.findById(id);
	}

	public void deleteById(UUID id) {
		publisherAccountRepository.deleteById(id);
	}
	
}
