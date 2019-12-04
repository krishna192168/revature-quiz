package com.revature.revaturequiz.dto;

import lombok.Data;

@Data
public class QuizPoolQuestionsDTO {
	private Integer questionId;
	private Integer quizPoolId;
	private Boolean isSticky;
	private Boolean isEvaluate;
}
