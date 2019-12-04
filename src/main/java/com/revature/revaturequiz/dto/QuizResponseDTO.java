package com.revature.revaturequiz.dto;

import lombok.Data;

@Data
public class QuizResponseDTO {
	private String quizName;
	private String levelName;
	private String categoryName;
	private String createdBy;
	private Boolean isStatus;
}
