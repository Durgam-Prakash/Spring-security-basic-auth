package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	
	
	@GetMapping("/demo1")
	public String getDetails() {
		return "This is DEMO 1...!";
	}
	
	
	@GetMapping("/demo2")
	public String getAll() {
		return "This is ALL DETAILS...!";
	}
	
	
	
	@GetMapping("/demo3")
	public String getUsers() {
		return "This is USERS ...!";
	}
	
	
	@GetMapping("/demo4")
	public String getproducts() {
		return "This is Products...!";
	}
}
