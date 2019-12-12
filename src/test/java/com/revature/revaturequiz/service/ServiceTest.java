package com.revature.revaturequiz.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.revaturequiz.dao.QuizDAO;
import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;


@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
	//Here create instance for quiz service
	@InjectMocks
	private QuizServiceImpl quizService;
	@Mock
	private QuizDAO quizDAO;
	//JUnit runner replace explicit initMocks call
//	@Before
//	public void mockSetup()
//	{
//		MockitoAnnotations.initMocks(this);
//	}
	//Here test find all quizzes
	@Test
	public void testFindAllQuizzes()
	{
		try {
			//Service
			List<QuizResponseDTO> quizzesDTO = new ArrayList<QuizResponseDTO>();
			//DAO
			List<Quiz> quizzesData = new ArrayList<Quiz>();
			
			Quiz quizObj = new Quiz();
			quizObj.setId(1);
			quizObj.setCreatedBy("krishna");
			
			quizzesData.add(quizObj);
			
			QuizResponseDTO quizDTOObj = new QuizResponseDTO();
			
			
			for(Quiz quiz : quizzesData)
			{
				quizDTOObj.setQuiz(quiz);
				quizzesDTO.add(quizDTOObj);
			}
			
			//Here mock dao method findAllQuizzes
			when(quizDAO.findAllQuizzes()).thenReturn(quizzesData);
			List<QuizResponseDTO> quizzes = quizService.findAllQuizzes();

			assertEquals(quizzes, quizzesDTO);
			
			
		} catch (DBException | ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void testFindPoolByQuizId()
	{
		
		try {
			QuizPool poolObj = new QuizPool();
			List<QuizPool> pools = new ArrayList<QuizPool>();
			List<QuizPoolQuestion> poolsQuestions = new ArrayList<QuizPoolQuestion>();
			PoolResponseDTO poolResponseObj = new PoolResponseDTO();
			PoolResponseDTO poolResponseDTO = new PoolResponseDTO();
			
			poolObj.setId(1);
			poolObj.setMaxNumerOfQuestion(4);
			poolObj.setName("javapool");
			poolObj.setQuizId(1);
			
			pools.add(poolObj);
			
			//Here set pool details in pool list
			poolResponseDTO.setPools(pools);
			//Here set pool questions details in pool list
			poolResponseDTO.setPoolQuestions(poolsQuestions);
			
			when(quizDAO.findPoolsByQuizId(1)).thenReturn(pools);
			
			poolResponseObj = quizService.findPoolsByQuizId(1);
			
			assertEquals(poolResponseDTO, poolResponseObj);
			
		} catch (DBException | ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateQuiz()
	{
		try {
			QuizDTO quizDTO = new QuizDTO();
			Quiz quizObj = new Quiz();
			
			quizObj.setId(1);
			quizObj.setName("java");
			quizObj.setTags("java,core java");
			quizObj.setActivityPoints(80);
			quizObj.setMaxNumbetOfAttempts(5);
			quizObj.setIsAttemptReview(false);
			quizObj.setIsShowWhetherCorrect(false);
			quizObj.setIsShowCorrectAnswer(false);
			quizObj.setIsShowAnswerExplanation(false);
			
			quizDTO.setQuiz(quizObj);
			Boolean isQuizCreated = false;
			
			when(quizDAO.createQuiz(Matchers.any(QuizDTO.class))).thenReturn(true);
			
			isQuizCreated = quizService.createQuiz(quizDTO);
			
			assertTrue(isQuizCreated);
			
		} catch (DBException | ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = ServiceException.class)
	public void negativeTestFindAllQuizzes() throws DBException, ServiceException
	{
			when(quizDAO.findAllQuizzes()).thenThrow(DBException.class);
			quizService.findAllQuizzes();
	}
	@Test(expected = ServiceException.class)
	public void negativetestFindPoolByQuizId() throws DBException, ServiceException
	{
		when(quizDAO.findPoolsByQuizId(Matchers.anyInt())).thenThrow(DBException.class);
		quizService.findPoolsByQuizId(1);
	}
	@Test(expected = ValidationException.class)
	public void negativetestCreateQuiz() throws DBException, ServiceException
	{
		Quiz quizObj = new Quiz();
		
		quizObj.setId(1);
		quizObj.setName("java");
		quizObj.setTags("java,core java");
		QuizDTO quiz = new QuizDTO();
		quiz.setQuiz(quizObj);
		quizService.createQuiz(quiz);
	}
}
