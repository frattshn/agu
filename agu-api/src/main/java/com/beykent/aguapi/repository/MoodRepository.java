package com.beykent.aguapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beykent.aguapi.entity.Mood;

public interface MoodRepository extends JpaRepository<Mood, Integer>{

}
