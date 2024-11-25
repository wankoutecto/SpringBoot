package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizWrapper;
import com.example.quizapp.model.Response;
import com.example.quizapp.service.QuizService;

@RestController
@RequestMapping("quizzes")
public class QuizController {
	@Autowired
	QuizService quizService;
	
	@PostMapping("create/{category}/{numQ}/{title}")
	public ResponseEntity<String>createQuiz(@PathVariable String category, @PathVariable int numQ, @PathVariable String title){
		return quizService.createQuiz(category, numQ, title);
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuizWrapper>> getQuizQuestion(@PathVariable int id){
		return quizService.getQuizQuestion(id);
	}
	
	@PostMapping("responseQuiz/{id}")
	public ResponseEntity<Integer> getReponseQuiz(@PathVariable int id, @RequestBody List<Response> responses){
		
		return quizService.getResponseQuiz(id, responses);
	}
}
