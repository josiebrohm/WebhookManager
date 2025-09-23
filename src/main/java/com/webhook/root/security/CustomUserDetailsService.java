package com.webhook.root.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final PublisherAccountRepository publisherRepository;

	public CustomUserDetailsService(PublisherAccountRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PublisherAccount publisher = publisherRepository.findByUsername(username);

		if (publisher == null) {
			throw new UsernameNotFoundException("Publisher not found with username " + username);
		}

		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + publisher.getRole());

		return new org.springframework.security.core.userdetails.User(
			publisher.getUsername(),
			publisher.getPassword(),
			List.of(authority)
		);
	}
	
}
