package com.beykent.aguapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.beykent.aguapi.entity.User;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.exception.ResourceNotFoundException;
import com.beykent.aguapi.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public User getUserById(Long id) {
		return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id : %d".formatted(id)));
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	public Long createUser(@Valid User user) {
		User savedUser;
		user.setIsActive(User.USER_ACTIVE);
		user.setCreatedTime(LocalDateTime.now());
		try {
			savedUser = this.userRepository.save(user);
		} catch (ValidationException e) {
			throw new InvalidParameterException("Invalid character!");
		} catch (DataIntegrityViolationException e) {
			throw new ResourceAlreadyExistsException("User already exists!");
		}
		return savedUser.getId();
	}
	
	public Long updateUser(Long id, @Valid User user) {
		User foundUser = getUserById(id);
		User updatedUser;
		
		foundUser.setNameSurname(user.getNameSurname());
		foundUser.setUserName(user.getUserName());
		foundUser.setPassword(user.getPassword());
		foundUser.setEmail(user.getEmail());
		foundUser.setBioContent(user.getBioContent());
		foundUser.setAvatar(user.getAvatar());
		foundUser.setBirthdate(user.getBirthdate());
		
		try {
			updatedUser = this.userRepository.save(foundUser);
		} catch (ValidationException e) {
			throw new InvalidParameterException("Invalid character!");
		} catch (DataIntegrityViolationException e) {
			throw new ResourceAlreadyExistsException("User already exists!");
		}
		return updatedUser.getId();
	}
	
	public Long changeUserActivity(Long id) {
		User user = getUserById(id);
		if (User.USER_ACTIVE.equals(user.getIsActive())) {
			this.userRepository.changeUserActivity(id ,User.USER_INACTIVE);
		} else {
			this.userRepository.changeUserActivity(id, User.USER_ACTIVE);
		}
		return user.getId();
	}
	
	public Void deleteUser(Long id) {
		try {
			this.userRepository.deleteById(id);
			return null;
		} catch (IllegalArgumentException e) {
			throw new InvalidParameterException("Id cannot be null!");
		}
	}
	
}
