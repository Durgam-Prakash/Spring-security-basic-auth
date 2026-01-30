package com.example.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.User;
import com.example.security.pojo.SignupData;
import com.example.security.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> createAccount(@RequestBody SignupData signupData) throws Exception{
		User registerUser = userService.registerUser(signupData);
		Map<String,Object> response = new HashMap<>();
		response.put("Status", "OK");
		response.put("Data", registerUser);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
}
