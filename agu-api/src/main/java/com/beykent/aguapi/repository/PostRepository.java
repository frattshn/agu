package com.beykent.aguapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beykent.aguapi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("SELECT p FROM post p " +
			"WHERE (:userId IS NULL OR p.user.id = :userId) " +
			"AND (:isPublic IS NULL OR p.isPublic = :isPublic) " +
			"AND (:moodId IS NULL OR p.mood.moodId = :moodId)"
		)
	public List<Post> findPostsByFilter(
			@Param("userId") Long userId,
			@Param("isPublic") Boolean isPublic,
			@Param("moodId") Integer moodId
		);
	

}
