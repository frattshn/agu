package com.beykent.aguapi.controller;

import com.beykent.aguapi.entity.Comment;
import com.beykent.aguapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController extends ErrorController {

    // createComment ve deleteCommente tekrar bakılacak.

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsWithUserIdOrPostId(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getCommentsWithUserIdOrPostId(userId, postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<Long> createComment(@RequestBody @Valid Comment comment, Long postId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.commentService.createComment(postId, comment));
    }

    @PutMapping
    public ResponseEntity<Long> updateComment(@RequestBody @Valid Comment comment){
        Long commentId = this.commentService.updateComment(comment);
        return ResponseEntity.status(HttpStatus.OK).body(commentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.deleteComment(id));
    }
}
