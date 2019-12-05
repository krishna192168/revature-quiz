package com.revature.revaturequiz.dto;

import java.util.List;

import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

import lombok.Data;

@Data
public class QuizDTO {
	 private Quiz quiz;
	 private List<QuizPool> quizPool;
	 private List<QuizPoolQuestion> poolQuestions;
}