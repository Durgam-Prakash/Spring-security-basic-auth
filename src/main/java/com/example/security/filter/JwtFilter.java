package com.example.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.service.CustomUserDetailsService;
import com.example.security.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired 
	private CustomUserDetailsService userDetailsService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		
		String userName = null;
		String token = null;
		
		if(authHeader!= null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			if(jwtUtil.validateToken(token)) {
				userName=jwtUtil.extractUserName(token);
			}
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		
		filterChain.doFilter(request, response);
		
	}

}
