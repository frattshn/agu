package com.beykent.aguapi.repository;

import com.beykent.aguapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM comment c "+
            "WHERE (:userId IS NULL OR c.user.id = :userId) " +
            "AND (:postId IS NULL OR c.post.id = :postId)"
    )
    List<Comment> getCommentsWithUserIdOrPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId
    );
}
