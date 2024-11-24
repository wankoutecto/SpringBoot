package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;

@Service
public class QuestionService {
	@Autowired
	QuestionRepository questionRepo;
	
	public ResponseEntity<List<Question>> getListQuestion(String category){
		try {
			return new ResponseEntity<>(questionRepo.findAllByCategory(category), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace(); 
		}
		return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
	}
}
