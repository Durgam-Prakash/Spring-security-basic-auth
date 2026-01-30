package com.example.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.entity.User;
import com.example.security.pojo.SignupData;
import com.example.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerUser(SignupData signupData) throws Exception {
		
		Optional<User> byUserName = userRepository.findByUserName(signupData.getUserName());
		if(byUserName.isPresent()) {
			throw new Exception("user alredy register please Login");
		}
		
		User user = new User();
		user.setUserName(signupData.getUserName());
		user.setUserEmail(signupData.getUserEmail());
		user.setPassword(passwordEncoder.encode(signupData.getPassword()));
		user.setRole("ROLE_USER");
		User savedUser = userRepository.save(user);
		return savedUser;
		    
		
		
		
	}

}
