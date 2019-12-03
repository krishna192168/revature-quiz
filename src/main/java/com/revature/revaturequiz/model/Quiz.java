package com.revature.revaturequiz.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="quizs")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String tags;
	private Integer activityPoints;
	//TODO:Time
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
	private LocalDate createdOn;
	private LocalDate modifiedOn;
	private String createdBy;
	private String modifiedBy;
}
