package com.revature.revaturequiz.dao;

import java.util.List;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;

public interface QuizDAO {
	public List<QuizResponseDTO> findAllQuizzes() throws DBException;
	public Boolean createQuiz(QuizDTO quiz) throws DBException;
}
