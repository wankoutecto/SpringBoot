package com.example.quizapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Response {
	@Id
	private Integer id;
	private String response;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
