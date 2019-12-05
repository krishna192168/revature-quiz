package com.revature.revaturequiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revaturequiz.dao.QuizDAO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.exception.ValidatorException;
import com.revature.revaturequiz.util.MessageConstant;
import com.revature.revaturequiz.validator.QuizValidator;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	private QuizDAO quizDAO;
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException
	{
		List<QuizResponseDTO> quizzesList;
		try {
			quizzesList = quizDAO.findAllQuizzes();
			if(quizzesList.isEmpty())
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_GET_QUIZZES);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return quizzesList;
	}
	public Boolean createQuiz(QuizDTO quiz) throws ServiceException
	{
		Boolean isQuizCreated = null;
		try {
			//Call create Quiz method in dao
			QuizValidator.quizValidator(quiz);
			isQuizCreated = quizDAO.createQuiz(quiz);
			if(isQuizCreated == false)
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_CREATE_QUIZ);
			}
		}catch(ValidatorException | DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return isQuizCreated;
	}
}