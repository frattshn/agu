package com.beykent.aguapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beykent.aguapi.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.is_active = :isActive WHERE u.id = :id", nativeQuery = true)
    public void changeUserActivity(@Param("id") Long id, @Param("isActive") Integer isActive);

}
