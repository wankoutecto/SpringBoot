package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Alien;

public interface AlienRepo extends JpaRepository<Alien, Integer>{
	List<Alien> findByAname(String aname);
	
	
}