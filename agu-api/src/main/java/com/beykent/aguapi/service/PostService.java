package com.beykent.aguapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.beykent.aguapi.entity.Post;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.repository.PostRepository;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	
	public List<Post> getAllPublicPosts(){
		return this.postRepository.getAllPublicPosts();
	}
	
	public List<Post> findPostsByFilter(Long userId, Boolean isPrivate, Integer moodId){
		return this.postRepository.findPostsByFilter(userId, isPrivate, moodId);
	}
	
	public Long createPost(Long userId, @Valid Post post) {
		Post savedPost;
		post.setCreatedTime(LocalDateTime.now());
		post.setUser(this.userService.getUserById(userId));
		try {
			savedPost = this.postRepository.save(post);
		} catch (ValidationException e) {
			throw new InvalidParameterException("Invalid character!");
		} catch (DataIntegrityViolationException e) {
			throw new ResourceAlreadyExistsException("Post already exists at this time : " + post.getPostedTime().toString() + " with this user : " + post.getUser().toString());
		}
		return savedPost.getId();
	}
	
	
	
}
