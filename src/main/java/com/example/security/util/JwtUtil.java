package com.example.security.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	
	private final String  SECRET_KEY = "mysecretkeymysecretkeymysecretkey123";
	
	private final long EEXPIRATION_MS = 1000 * 60 * 60;
	
	
	private Key getSigniInKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	
	
	
	public String generateToken(String userName,String role) {
		
		return Jwts.builder()
		.setSubject(userName)
		.claim("role", role)
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis()+EEXPIRATION_MS))
		.signWith(getSigniInKey(),SignatureAlgorithm.HS256)
		.compact();
	}
	
	
	public String extractUserName(String token) {
		return parseToken(token).getBody().getSubject();
	}
	
	public String extractRole(String token) {
		return (String) parseToken(token).getBody().get("role");
	}
	
	
	public boolean validateToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (JwtException e) {
			
			return false;
		}
	}
	
	
	private Jws<Claims> parseToken(String token){
		return Jwts.parserBuilder()
				.setSigningKey(getSigniInKey())
				.build()
				.parseClaimsJws(token);
	}
	

}
