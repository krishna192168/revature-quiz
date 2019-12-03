package com.revature.revaturequiz.model;

import lombok.Data;

@Data
public class QuizPoolQuestion {
	private Integer id;
	private Integer questionId;
	private Integer quizPoolId;
	private Boolean isSticky;
	private Boolean isEvaluate;
}
