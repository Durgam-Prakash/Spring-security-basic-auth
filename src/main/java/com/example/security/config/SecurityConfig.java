package com.example.security.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.filter.JwtFilter;
import com.example.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		http
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth
				
				.requestMatchers("/auth/register","/test/demo2","/test/demo4","/auth/login").permitAll()
				.requestMatchers("/test/demo1","/test/demo3").hasRole("USER")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				
				.anyRequest().authenticated()
				
				)
//		.httpBasic(Customizer.withDefaults());
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	
	}
	

	@Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
//	@Bean
//	 AuthenticationManager authManager(HttpSecurity http)throws Exception {
//		return http.getSharedObject(AuthenticationManagerBuilder.class)
//				.userDetailsService(userDetailsService)
//				.passwordEncoder( passwordEncoder())
//				.and()
//				.build();
//				
//		
//	}
	
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authBuilder.userDetailsService(userDetailsService)
	               .passwordEncoder(passwordEncoder());
	    return authBuilder.build();
	}
	
	
}
