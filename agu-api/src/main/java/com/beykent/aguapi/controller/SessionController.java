package com.beykent.aguapi.controller;

import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beykent.aguapi.request.UserLoginRequest;

@RestController
@RequestMapping("/user")
public class SessionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String sessionId = UUID.randomUUID().toString();
        RedisConnection redisConnection = redisConnectionFactory.getConnection();

        // Store the session ID in Redis with a TTL of 30 minutes
        redisConnection.setEx(sessionId.getBytes(), 1800, authentication.getName().getBytes());

        // Set the session ID in a cookie and return it in the response
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/session")
    public Authentication getSession(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String sessionId = getSessionId(request);
        if (sessionId != null) {
            RedisConnection redisConnection = redisConnectionFactory.getConnection();

            // Remove the session ID from Redis
            redisConnection.del(sessionId.getBytes());

            // Remove the session ID cookie from the response
            Cookie sessionCookie = new Cookie("SESSIONID", "");
            sessionCookie.setPath("/");
            sessionCookie.setMaxAge(0);
            response.addCookie(sessionCookie);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.setStatus(HttpStatus.OK.value());
    }

    private String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SESSIONID")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}