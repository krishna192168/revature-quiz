package com.revature.revaturequiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.revaturequiz.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	@Query("SELECT name,created_by,updated_on FROM quizs")
	List<Quiz> findAllQuiz();
}
