package com.revature.revaturequiz.dto;

import java.util.List;

import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

import lombok.Data;

@Data
public class PoolResponseDTO {
	private List<QuizPool> pools;
	private List<QuizPoolQuestion> poolQuestions;
}
