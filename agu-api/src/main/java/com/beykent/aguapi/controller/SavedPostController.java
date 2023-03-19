package com.beykent.aguapi.controller;

import com.beykent.aguapi.entity.SavedPost;
import com.beykent.aguapi.service.SavedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/savedPost")
@RequiredArgsConstructor
public class SavedPostController {

    private final SavedPostService savedPostService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedPost>> getSavedPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.savedPostService.getSavedPostsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Long> addSavedPost(@RequestParam Long userId, @RequestParam Long postId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.savedPostService.addSavedPost(userId, postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSavedPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.savedPostService.removeSavedPost(id));
    }

}
