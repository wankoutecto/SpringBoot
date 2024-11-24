package com.example.quizapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.quizapp.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	List<Question> findAllByCategory(String category);
	
	@Query(value= "SELECT * FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
	List<Question> findRandomQuestionByCategory(String category, int numQ);

}
