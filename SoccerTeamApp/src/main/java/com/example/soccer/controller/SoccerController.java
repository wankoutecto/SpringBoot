package com.example.soccer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.soccer.model.SoccerEntity;
import com.example.soccer.service.SoccerService;

import jakarta.validation.constraints.NotNull;

@RestController
@Validated
public class SoccerController {

	private final SoccerService ss;
	public SoccerController(SoccerService soccerservice) {
		this.ss = soccerservice;
	}
	
	@GetMapping(path="/team")
	public List<SoccerEntity> getAllTeam(){
		return ss.getAllTeam();
	}
	
	@GetMapping(path="/team/{ide}")
	public Optional<SoccerEntity> getTeam(@PathVariable int ide){
		return ss.getTeam(ide);
	}
	
	@PostMapping("/team")
	public SoccerEntity addTeam(@RequestBody SoccerEntity team){
		return ss.addTeam(team);
	}
	
	@DeleteMapping(path="/team/{ide}")
	public void deleteTeam(@PathVariable int ide){
		ss.deleteTeam(ide);
	}
	
	@PutMapping(path="/team")
	public SoccerEntity updateTeam(@RequestBody SoccerEntity team){
		return ss.updateTeam(team);
	}
}
