package com.revature.revaturequiz.service;

import java.util.List;

import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.exception.ValidatorException;

public interface QuizService {
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException, DBException;
	public Boolean createQuiz(QuizDTO quiz) throws ServiceException, ValidatorException, DBException;
	public PoolResponseDTO findPoolsByQuizId(final int quizId) throws ServiceException, DBException;
}
