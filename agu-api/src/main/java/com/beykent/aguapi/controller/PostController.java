package com.beykent.aguapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.beykent.aguapi.entity.Post;
import com.beykent.aguapi.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
public class PostController extends ErrorController{
	
	private final PostService postService;
	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPublicPosts(){
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findPostsByFilter(null, Post.PUBLIC_POST,null));
	}

	@GetMapping("/search")
	public ResponseEntity<List<Post>> findPostsByFilter(@RequestParam(required = false) Long userId, @RequestParam(required = false) Boolean isPublic, @RequestParam(required = false) Integer moodId){
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findPostsByFilter(userId, isPublic, moodId));
	}

	@PostMapping("/{userId}")
	public ResponseEntity<Long> createPost(@PathVariable Long userId, @RequestBody @Valid Post post){
		return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(userId, post));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Long> updatePost(@PathVariable Long id, @RequestBody @Valid Post post) {
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePost(id, post));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.deletePost(id));
	}

}
