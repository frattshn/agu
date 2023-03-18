package com.beykent.aguapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPublicPosts());
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Post>> getPostsByMoodId(@RequestParam(required = false) Long userId, @RequestParam(required = false) Boolean isPrivate, @RequestParam(required = false) Integer moodId){
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findPostsByFilter(userId, isPrivate, moodId));
	}
	
	
	
	@PostMapping("/{userId}")
	public ResponseEntity<Long> createPost(@PathVariable Long userId, @RequestBody @Valid Post post){
		return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(userId, post));
	}
	
	

}
