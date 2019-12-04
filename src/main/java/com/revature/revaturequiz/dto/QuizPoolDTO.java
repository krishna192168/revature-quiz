package com.revature.revaturequiz.dto;

import lombok.Data;

@Data
public class QuizPoolDTO {
	private String name;
	private int maxNumberOfQuestions;
	private int quizId;
}
