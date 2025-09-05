package com.webhook.root.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.service.PublisherAccountService;

@RestController
@RequestMapping("/accounts")
public class PublisherAccountController {
	private final PublisherAccountService accountService;

	public PublisherAccountController(PublisherAccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public List<PublisherAccount> getAllAccounts() {
		return this.accountService.getAllAccounts();
	}
}
