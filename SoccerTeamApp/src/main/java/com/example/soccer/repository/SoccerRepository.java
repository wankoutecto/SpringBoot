package com.example.soccer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.soccer.model.SoccerEntity;

public interface SoccerRepository extends JpaRepository<SoccerEntity, Integer> {

}
