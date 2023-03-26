package com.beykent.aguapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beykent.aguapi.entity.User;
import com.beykent.aguapi.request.LoginRequest;
import com.beykent.aguapi.security.config.JwtConfig;
import com.beykent.aguapi.service.UserService;
import com.beykent.aguapi.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(exposedHeaders = {"Authorization", "UserId", "UserRole"}, origins = "http://localhost:3000")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private final JwtUtils jwtUtils;
	private final JwtConfig jwtConfig;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest request) {
		
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
		
		if (userDetails != null) {
			User user = this.userService.getUserByUserName(request.getUserName());
			if (User.USER_INACTIVE.equals(user.getIsActive())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not active.");
			}
			
			String token = jwtUtils.generateToken(userDetails);
			Long userId = jwtUtils.getUserIdFromToken(token);
			
			return ResponseEntity.status(HttpStatus.OK)
					.header(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token)
					.header("UserId", userId.toString())
					.header("UserRole", userDetails.getAuthorities().toString().replace("[", "").replace("]", ""))
					.body("Token created!");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}
	
}