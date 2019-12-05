package com.revature.revaturequiz.model;

import java.sql.Time;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Quiz {
	private Integer id;
	private String name;
	private String tags;
	private Integer activityPoints;
	private Time duration;
	private Integer maxNumbetOfAttempts;
	private Boolean isLevelOverride;
	private String slugUrl;
	private String description;
	private String metaKeywords;
	private String metaDescription;
	private String iconUrl;
	private String quizInstructions;
	private Integer levelId;
	private Integer categoryId;
	private Integer passPercentage;
	private Boolean isSlugUrlAccess;
	private Boolean isQuizTimerEnnable;
	private Boolean isShuffleQuestion;
	private Boolean isShuffleAnswer;
	private Boolean isDisplayScoreResult;
	private Boolean isAttemptReview;
	private Boolean isShowWhetherCorrect;
	private Boolean isShowCorrectAnswer;
	private Boolean isShowAnswerExplanation;
	private Boolean isSaveAndResume;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String createdBy;
	private String modifiedBy;
	private Boolean isSatus;
}