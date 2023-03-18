package com.beykent.aguapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beykent.aguapi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "SELECT * FROM post p WHERE p.is_private = 0", nativeQuery = true)
	public List<Post> getAllPublicPosts();
	

}
