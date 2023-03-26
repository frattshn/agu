package com.beykent.aguapi.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecretKeyConfig {
	
	private final JwtConfig jwtConfig;

	@Bean
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}

}
