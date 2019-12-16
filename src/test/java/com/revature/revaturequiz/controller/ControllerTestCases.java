package com.revature.revaturequiz.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.service.QuizService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTestCases {
	@Rule
	public JUnitRestDocumentation restDoc = new JUnitRestDocumentation("target/generated-snippets");
	//Here where we stored snippets
	@InjectMocks
	private QuizController  quizController;
	@Mock
	private QuizService quizService;
	private MockMvc mockMvc;
	
	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(quizController)
				.apply(documentationConfiguration(this.restDoc))
				.build();
	}
	
	@Test
	public void testFindAllQuizzes() throws Exception
	{
		Quiz quiz = new Quiz();
		quiz.setName("quiz name");
		QuizResponseDTO response = new QuizResponseDTO();
		response.setQuiz(quiz);
		List<QuizResponseDTO> quizzesList = new ArrayList<QuizResponseDTO>();
		quizzesList.add(response);
		when(quizService.findAllQuizzes()).thenReturn(quizzesList);
		
		this.mockMvc.perform(get("/quizzes"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andDo(print())
		.andDo(document("find_all_quizzes")); 
	}
}
