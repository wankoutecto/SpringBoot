package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizWrapper;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;

@Service
public class QuizService {
	@Autowired
	QuizRepository quizRepo;
	
	@Autowired
	QuestionRepository questionRepo;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Question> questions = questionRepo.findRandomQuestionByCategory(category, numQ);
		if (questions.size() < numQ) {
	        return new ResponseEntity<>("Not enough questions in the category", HttpStatus.BAD_REQUEST);
	    }
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizRepo.save(quiz);
		return new ResponseEntity<>("Quiz created successfully", HttpStatus.OK);
	}

	public ResponseEntity<List<QuizWrapper>> getQuizQuestion(int id) {
		try {
		Optional<Quiz> quiz = quizRepo.findById(id);
		if(quiz.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<Question> questionsFromDB = quiz.get().getQuestions();
		List<QuizWrapper> questionsForUser = new ArrayList<>();
		for(Question q: questionsFromDB) {
			QuizWrapper qw = new QuizWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}

			return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
	}
}
