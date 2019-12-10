package com.revature.revaturequiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revaturequiz.dao.QuizDAO;
import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.exception.ValidatorException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.util.MessageConstant;
import com.revature.revaturequiz.validator.QuizValidator;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	private QuizDAO quizDAO;
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException
	{
		final List<QuizResponseDTO> quizzesDTO = new ArrayList<>();
		final QuizResponseDTO quizResDTO = new QuizResponseDTO();
		try {
			List<Quiz> quizzes = quizDAO.findAllQuizzes();
			quizzes.stream()
			.forEach(
						(quizObj) -> {
							quizResDTO.setQuiz(quizObj);
							quizzesDTO.add(quizResDTO);
						}
					);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return quizzesDTO;
	}
	public PoolResponseDTO findPoolsByQuizId(final int quizId) throws ServiceException
	{
		
		final PoolResponseDTO poolResponseObj = new PoolResponseDTO();
		try {
			final List<QuizPool> pools = quizDAO.findPoolsByQuizId(quizId);
			if(pools.isEmpty())
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_GET_POOLS);
			}
			poolResponseObj.setPools(pools);
			pools.stream()
			.forEach(
					(poolObj) -> {
						try {
							poolResponseObj.setPoolQuestions(quizDAO.findPoolQuestions(poolObj.getId()));
						} catch (DBException e) {
							e.printStackTrace();
						}
					}
					);
		}
		catch(DBException e)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_GET_POOLS);
		}
		return poolResponseObj;
	}
	public Boolean createQuiz(QuizDTO quiz) throws ServiceException
	{
		Boolean isQuizCreated = null;
		try {
			//Call create Quiz method in dao
			QuizValidator.quizValidator(quiz);
			isQuizCreated = quizDAO.createQuiz(quiz);
			if(Boolean.FALSE.equals(isQuizCreated))
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_CREATE_QUIZ);
			}
		}catch(ValidatorException | DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return isQuizCreated;
	}
}