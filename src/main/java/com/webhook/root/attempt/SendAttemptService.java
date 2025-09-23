package com.webhook.root.attempt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.webhook.root.model.SendAttempt;
import com.webhook.root.repository.SendAttemptRepository;

@Service
public class SendAttemptService {
	private final SendAttemptRepository sendAttemptRepository;

	public SendAttemptService(SendAttemptRepository sendAttemptRepository) {
		this.sendAttemptRepository = sendAttemptRepository;
	}

	public SendAttempt addSendAttempt(SendAttempt sendAttempt) {
		sendAttemptRepository.save(sendAttempt);
		return sendAttempt;
	}

	public List<SendAttempt> getAllSendAttempts() {
		return sendAttemptRepository.findAll();
	}

	public Optional<SendAttempt> findById(UUID id) {
		return sendAttemptRepository.findById(id);
	}

	public void deleteById(UUID id) {
		sendAttemptRepository.deleteById(id);
	}
}