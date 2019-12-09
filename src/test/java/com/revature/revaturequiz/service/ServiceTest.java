package com.revature.revaturequiz.service;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.revaturequiz.dao.QuizDAO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.model.Quiz;


@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
	//Here create instance for quiz service
	@InjectMocks
	private QuizServiceImpl quizService;
	@Mock
	private QuizDAO quizDAO;
	@Before
	public void mockSetup()
	{
		MockitoAnnotations.initMocks(this);
	}
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
			
			
			when(quizDAO.findAllQuizzes()).thenReturn(quizzesData);
			List<QuizResponseDTO> quizzes = quizService.findAllQuizzes();
			quizzesDTO.forEach(
					(quiz) ->{
						System.err.println(quiz);
					}
				);
			assertSame(quizzesDTO,quizzes);
//			assertNotNull(quizzesDTO);
			
			
		} catch (DBException | ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
