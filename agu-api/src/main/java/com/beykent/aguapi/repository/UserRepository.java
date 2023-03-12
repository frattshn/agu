package com.beykent.aguapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beykent.aguapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
//	@Modifying
//	@Query("UPDATE user u SET u.is_active = :activity WHERE u.id = :id")
//	public void changeUserActivity(@Param("id") Long id, @Param("activity") Integer activity);
	

}
