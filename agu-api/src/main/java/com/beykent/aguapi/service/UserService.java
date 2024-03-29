package com.beykent.aguapi.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beykent.aguapi.entity.User;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.exception.ResourceNotFoundException;
import com.beykent.aguapi.repository.UserRepository;
import com.beykent.aguapi.request.UserRegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User getUserById(Long id) {
		return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id"));
		// : %d".formatted(id)
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				Collections.emptyList());
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	public Long createUser(@Valid UserRegisterRequest userRegisterRequest) {
		User savedUser = new User();
		savedUser.setUserName(userRegisterRequest.getUserName());
		savedUser.setEmail(userRegisterRequest.getEmail());
		savedUser.setIsActive(User.USER_ACTIVE);
		savedUser.setCreatedTime(LocalDateTime.now());
		savedUser.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
		try {
			savedUser = this.userRepository.save(savedUser);
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
	
}