package com.example.soccer.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.example.soccer.model.SoccerEntity;
import com.example.soccer.repository.SoccerRepository;

@Service
public class SoccerService {
	
	private final SoccerRepository soccerRepo;
	public SoccerService(SoccerRepository soccerRepo) {
		this.soccerRepo = soccerRepo;
	}
	
	public SoccerEntity addTeam(SoccerEntity team){
		return soccerRepo.save(team);
	}

	public List<SoccerEntity> getAllTeam() {
		return soccerRepo.findAll();
	}
	
	public void deleteTeam(int id) {
		soccerRepo.deleteById(id);
	}
	
	public Optional<SoccerEntity> getTeam(int id) {
		return soccerRepo.findById(id);
	}
	
	public SoccerEntity updateTeam (SoccerEntity team) {
		return soccerRepo.save(team);
	}
}

