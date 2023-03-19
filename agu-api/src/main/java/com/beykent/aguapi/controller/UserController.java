package com.beykent.aguapi.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.beykent.aguapi.entity.User;
import com.beykent.aguapi.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController extends ErrorController{
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
	}

	@PostMapping
	public ResponseEntity<Long> createUser(@RequestBody @Valid User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Long> updateUser(@PathVariable Long id, @RequestBody @Valid User user){
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateUser(id, user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.userService.deleteUser(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Long> changeUserActivity(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.changeUserActivity(id));
	}
}
