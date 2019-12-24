package com.revature.revaturequiz.model;

import lombok.Data;

@Data
public class QuizPool {
	private int id;
	private String name;
	private Integer maxNumerOfQuestion;
	private Integer quizId;
}
