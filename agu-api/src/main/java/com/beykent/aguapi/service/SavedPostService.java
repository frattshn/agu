package com.beykent.aguapi.service;

import com.beykent.aguapi.entity.SavedPost;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.repository.SavedPostRepository;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedPostService {

    private final SavedPostRepository savedPostRepository;
    private final UserService userService;
    private final PostService postService;

    public List<SavedPost> getSavedPostsByUserId(Long userId) {
        return this.savedPostRepository.findByUserId(userId).orElse(new ArrayList<>());
    }

    public Long addSavedPost(Long userId, Long postId) {
        SavedPost savedPost = new SavedPost();
        savedPost.setUser(Arrays.asList(this.userService.getUserById(userId)));
        savedPost.setPost(this.postService.getPostById(postId));
        savedPost.setSavedTime(LocalDateTime.now());
        try {
            savedPost = this.savedPostRepository.save(savedPost);
        } catch (ValidationException e) {
            throw new InvalidParameterException("Invalid character!");
        }
        return savedPost.getId();
    }

    public Void removeSavedPost(Long id) {
        try {
            this.savedPostRepository.deleteById(id);
            return null;
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Id cannot be null!");
        }
    }
}
