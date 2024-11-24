package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Alien;
import com.example.demo.repo.AlienRepo;


@RestController
public class AlienController {
	
	@Autowired
	AlienRepo repo;
	
	@RequestMapping("/")
	public String home() {	
		return "home";
	}
	
	@PostMapping("/addAlien")
	public Alien addAlien(@RequestBody Alien alien) {
		repo.save(alien);
		return alien;
	}
	
	@GetMapping("/alien")
	@ResponseBody //to return actual data not just view name
	public List<Alien> getAlien() {
		
		return repo.findAll(); 
	}
	
	@GetMapping("/alien/{aid}") 
	public Optional<Alien> getAlien(@PathVariable int aid) {
		
		return repo.findById(aid); 
	}
	
	@DeleteMapping("/deleteAlien/{aid}")
	public void deleteAlien(@PathVariable int aid) {
		repo.deleteById(aid); 
	}
	
	@PutMapping("/updateAlien")
	public Alien updateAlien(@RequestBody Alien alien) {
		return repo.save(alien);
	}
	
}
 