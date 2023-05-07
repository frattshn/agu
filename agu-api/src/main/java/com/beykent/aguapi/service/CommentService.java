package com.beykent.aguapi.service;

import com.beykent.aguapi.entity.Comment;
import com.beykent.aguapi.exception.InvalidParameterException;
import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.exception.ResourceNotFoundException;
import com.beykent.aguapi.repository.CommentRepository;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public List<Comment> getCommentsWithUserIdOrPostId(Long userId, Long postId) {
        return this.commentRepository.getCommentsWithUserIdOrPostId(userId, postId);
    }

    public Comment getCommentById(Long commentId) {
        return this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with this id :"));
        //  %d".formatted(commentId)
    }

    public Long createComment(Long postId, @Valid Comment comment) {
        Comment savedComment;
        comment.setCreatedTime(LocalDateTime.now());
        comment.setPost(this.postService.getPostById(postId));
        try {
            savedComment = this.commentRepository.save(comment);
        } catch (ValidationException e) {
            throw new InvalidParameterException("Invalid character!");
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Comment already exists!");
        }
        return savedComment.getId();
    }

    public Long updateComment(@Valid Comment comment) {
        Comment foundComment = getCommentById(comment.getId());
        Comment updatedComment;

        foundComment.setText(comment.getText());

        updatedComment = this.commentRepository.save(foundComment);

        return updatedComment.getId();
    }

    public Object deleteComment(Long id) {
        try {
            this.commentRepository.deleteById(id);
            return null;
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Id cannot be null!");
        }
    }
}
