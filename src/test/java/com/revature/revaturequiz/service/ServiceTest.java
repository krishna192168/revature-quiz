package com.revature.revaturequiz.service;

import static org.junit.Assert.assertEquals;
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
			List<QuizResponseDTO> quizzesDTO = new ArrayList<QuizResponseDTO>();
			Quiz quizObjA = new Quiz();
			quizObjA.setId(1);
			quizObjA.setCreatedBy("krishna");
			
			QuizResponseDTO quizDTOObjA = new QuizResponseDTO();
			quizDTOObjA.setQuiz(quizObjA);
			
			quizzesDTO.add(quizDTOObjA);
			
			when(quizService.findAllQuizzes())
			.thenThrow(ServiceException.class);
			
			assertEquals(quizzesDTO,quizService.findAllQuizzes());
			
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
