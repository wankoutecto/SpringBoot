package com.example.soccer.model;

import java.util.List;
import java.util.Optional;


import javax.management.RuntimeErrorException;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import com.example.soccer.repository.SoccerRepository;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SoccerEntity {
	@Id
	@GeneratedValue
	@Min(value = 1, message = "id must be greater than or equal to 1")
	private int id;
	
	@NotNull(message = "Team name cannot be null")
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int ide) {
		this.id = ide;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Team [ide=" + id + ", name=" + name + "]";
	}	
		
}

