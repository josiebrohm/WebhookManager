package com.webhook.root.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;

public class SecurityUtil {

    public static PublisherAccount getCurrentPublisher(PublisherAccountRepository publisherRepository) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return publisherRepository.findByUsername(username);
    }
}
