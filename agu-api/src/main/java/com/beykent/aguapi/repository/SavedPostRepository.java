package com.beykent.aguapi.repository;

import com.beykent.aguapi.entity.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

    Optional<List<SavedPost>> findByUserId(Long userId);

}
