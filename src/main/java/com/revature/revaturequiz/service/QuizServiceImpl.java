package com.revature.revaturequiz.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger serviceLogger = LoggerFactory.getLogger("QuizService");
	@Autowired
	private QuizDAO quizDAO;
	public List<QuizResponseDTO> findAllQuizzes() throws ServiceException
	{
		serviceLogger.info("Start find all quizzes");
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
			serviceLogger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return quizzesDTO;
	}
	public PoolResponseDTO findPoolsByQuizId(final int quizId) throws ServiceException
	{
		
		final PoolResponseDTO poolResponseObj = new PoolResponseDTO();
		try {
			serviceLogger.info("Start find pools by id");
			final List<QuizPool> pools = quizDAO.findPoolsByQuizId(quizId);
			poolResponseObj.setPools(pools);
			pools.stream()
			.forEach(
					(poolObj) -> {
						try {
							poolResponseObj.setPoolQuestions(quizDAO.findPoolQuestions(poolObj.getId()));
						} catch (DBException e) {
							serviceLogger.error(e.getMessage(),e);
						}
					}
					);
		}
		catch(DBException e)
		{
			serviceLogger.error(e.getMessage(),e);
			throw new ServiceException(MessageConstant.UNABLE_TO_GET_POOLS);
		}
		return poolResponseObj;
	}
	public Boolean createQuiz(QuizDTO quiz) throws ServiceException
	{
		Boolean isQuizCreated = null;
		try {
			serviceLogger.info("Start create quiz");
			//Call create Quiz method in quizdao
			QuizValidator.quizValidator(quiz);
			isQuizCreated = quizDAO.createQuiz(quiz);
		}catch(ValidatorException | DBException e) {
			serviceLogger.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage());
		}
		return isQuizCreated;
	}
}