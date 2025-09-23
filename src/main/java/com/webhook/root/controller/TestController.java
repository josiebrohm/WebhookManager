package com.webhook.root.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user")
	public String userAccess() {
		return "User content";
	}

	@GetMapping("/admin")
	public String adminAccess() {
		return "Admin content";
	}
	
	
}
