package com.revature.revaturequiz.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

public class TestDAO {
	private QuizDAO quizDAO = new QuizDAOImpl();
	@Test
	public void testFindAllQuizzes()
	{
		List<Quiz> quiz = null;
		try {
			quiz = quizDAO.findAllQuizzes();
			assertNotNull(quiz);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFindAllPools()
	{
		List<QuizPool> pools = null;
		pools = quizDAO.findPools(1);
		assertNotNull(pools);
	}
	@Test
	public void testFindPoolQuestions()
	{
		List<QuizPoolQuestion> poolQuestions = null;
		poolQuestions = quizDAO.findPoolQuestions(1);
		assertNotNull(poolQuestions);
	}
}
