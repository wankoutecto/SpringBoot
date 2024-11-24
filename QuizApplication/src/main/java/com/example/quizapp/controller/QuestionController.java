package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;

@RestController
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@GetMapping("question/{category}")
	public ResponseEntity<List<Question>> getQuestion(@PathVariable String category){
		return questionService.getListQuestion(category);
	}
}
