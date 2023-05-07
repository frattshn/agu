package com.beykent.aguapi.service;

import java.time.LocalDateTime;
import java.util.List;

import com.beykent.aguapi.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.beykent.aguapi.entity.Post;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.repository.PostRepository;

import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public Post getPostById(Long postId) {
		return this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id : "));
		// %d".formatted(postId)
	}
	
	public List<Post> findPostsByFilter(Long userId, Boolean isPublic, Integer moodId){
		return this.postRepository.findPostsByFilter(userId, isPublic, moodId);
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

	public Long updatePost(Long id, @Valid Post post) {
		Post foundPost = getPostById(id);
		Post updatedPost;

		foundPost.setTitle(post.getTitle());
		foundPost.setText(post.getText());
//		foundPost.setMood(); // mood service ve repository olusturulacak.
		foundPost.setIsPublic(post.getIsPublic());
		foundPost.setPostedTime(post.getPostedTime());
		foundPost.setUpdatedTime(LocalDateTime.now());

		try {
			updatedPost = this.postRepository.save(foundPost);
		} catch (ValidationException e) {
			throw new InvalidParameterException("Invalid character!");
		} catch (DataIntegrityViolationException e) {
			throw new ResourceAlreadyExistsException("Post already exists at this time : " + post.getPostedTime().toString() + " with this user : " + post.getUser().toString());
		}
		return  updatedPost.getId();
	}

	public Void deletePost(Long id) {
		try {
			Post post = getPostById(id);
			this.postRepository.deleteById(post.getId());
			return null;
		} catch (IllegalArgumentException e) {
			throw new InvalidParameterException("Id cannot be null!");
		}
	}
}
