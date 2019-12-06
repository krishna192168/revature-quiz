package com.revature.revaturequiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revaturequiz.dao.QuizDAO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.exception.ValidatorException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;
import com.revature.revaturequiz.util.MessageConstant;
import com.revature.revaturequiz.validator.QuizValidator;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	private QuizDAO quizDAO;
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException
	{
		List<QuizResponseDTO> quizzesList;
		List<Quiz> quizzes = null;
		List<QuizPool> pools = null;
		List<QuizPoolQuestion> questions = null;
		QuizResponseDTO quizResDTO = null;
		try {
			quizResDTO = new QuizResponseDTO();
			quizzesList = new ArrayList<QuizResponseDTO>();
			quizzes = quizDAO.findAllQuizzes();
			Integer quizId = null;
			for(Quiz quizObj : quizzes)
			{
				quizResDTO.setQuiz(quizObj);
				quizzesList.add(quizResDTO);
				quizId = quizObj.getId();
				pools = quizDAO.findPools(quizId);
				quizResDTO.setPools(pools);
				for(QuizPool pool : pools)
				{
					questions = quizDAO.findPoolQuestions(pool.getId());
					
				}
				quizResDTO.setPoolQuestions(questions);
			}
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