package com.revature.revaturequiz.service;

import java.util.List;

import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.ServiceException;

public interface QuizService {
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException;
	public Boolean createQuiz(QuizDTO quiz) throws ServiceException;
	public PoolResponseDTO findPoolByQuizId(int quizId);
}
