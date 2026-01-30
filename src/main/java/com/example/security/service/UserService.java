package com.example.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.entity.User;
import com.example.security.pojo.LoginData;
import com.example.security.pojo.SignupData;
import com.example.security.repository.UserRepository;
import com.example.security.util.JwtUtil;

@Service
public class UserService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
	
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
	
	
	
	
		
		 public String login(LoginData loginData) throws BadCredentialsException {
		        // Authenticate credentials
		        authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(loginData.getUserEmail(), loginData.getPassword())
		        );

		        // Load user details
		        UserDetails userDetails = userDetailsService.loadUserByUsername(loginData.getUserEmail());

		        // Extract role
		        String role = userDetails.getAuthorities().stream()
		                .map(auth -> auth.getAuthority())
		                .findFirst()
		                .orElse("ROLE_USER");

		        // Generate JWT token
		        return jwtUtil.generateToken(userDetails.getUsername(), role);
	}

}
