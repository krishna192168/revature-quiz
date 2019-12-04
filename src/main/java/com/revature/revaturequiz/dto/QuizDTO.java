package com.revature.revaturequiz.dto;

import java.sql.Time;

import lombok.Data;

@Data
public class QuizDTO {
	private String name;
	private String tags;
	private Integer activityPoints;
	private String slugUrl;
	private Integer levelId;
	private Integer categoryId;
	private Integer passPercentage;
	private String createdBy;
	private Integer maxNoOfAttempts;
	//TODO:time
//	private Time duration;
	private Boolean isLevelOverride;
	private String description;
	//Quiz pool
	private String quizPoolName;
	private Integer maxNumberOfQuestions;
	private Integer quizId;
	//Quiz pool questions
	private Integer questionId;
	private Integer quizPoolId;
	private Boolean isSticky;
	private Boolean isEvaluate;
}
