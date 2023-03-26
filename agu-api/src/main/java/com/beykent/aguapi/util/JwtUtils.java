package com.beykent.aguapi.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.beykent.aguapi.security.config.JwtConfig;
import com.beykent.aguapi.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {
	
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	private final UserService userService;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody().getSubject();
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody().getExpiration();
	}
	
	public Long getUserIdFromToken(String token) {
		String userName = getUsernameFromToken(token);
		return userService.getUserByUserName(userName).getId();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token).getBody();
	}
	
	// check token is expired or not
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return LocalDateTime.from(expiration.toInstant())
				.isBefore(LocalDateTime.from(expiration.toInstant()).plusDays(jwtConfig.getTokenExpirationAfterDays()));
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userDetails);
	}
	
	public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return createToken(claims, userDetails);
	}
	
	// generate token for user
	private String createToken(Map<String, Object> claims, UserDetails userDetails) {
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities())
				.setIssuedAt(new java.util.Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
				.signWith(secretKey)
				.compact();
		return token;
	}
	
	// validate token
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}