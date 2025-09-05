package com.webhook.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;
import com.webhook.root.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	PublisherAccountRepository publisherRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtils;

	@PostMapping("/login")
	public String authenticateUser(@RequestBody PublisherAccount publisher) {
		Authentication auth = authManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				publisher.getUsername(),
				publisher.getPassword()
			)
		);

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		return jwtUtils.generateToken(userDetails.getUsername());
	}

	@PostMapping("/register")
	public String registerUser(@RequestBody PublisherAccount publisher) {
		if (publisherRepository.existsByUsername(publisher.getUsername())) {
			return "Error: Username already taken!";
		}

		// create new publisher account
		PublisherAccount newPublisher = new PublisherAccount(
			publisher.getName(),
			publisher.getUsername(),
			encoder.encode(publisher.getPassword()),
			100
		);

		publisherRepository.save(newPublisher);
		return "User registered successfully!";
	}
	
	
}
