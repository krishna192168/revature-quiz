package com.revature.revaturequiz.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.service.QuizService;
import com.revature.revaturequiz.util.MessageConstant;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTestCases {
	private static final Class QuizDTO = null;
	@Rule
	public JUnitRestDocumentation restDoc = new JUnitRestDocumentation("target/generated-snippets");
	//Here where we stored snippets
	@InjectMocks
	private QuizController  quizController;
	@Mock
	private QuizService quizService;
	private MockMvc mockMvc;
	private ObjectMapper objMapper;
	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(quizController)
				.apply(documentationConfiguration(this.restDoc))
				.build();
		
		objMapper = new ObjectMapper();
	}
	
	@Test
	public void testFindAllQuizzes() throws Exception
	{
		Quiz quiz = new Quiz();
		quiz.setId(1);
		quiz.setName("java");
		quiz.setTags("java,java intro");
		QuizResponseDTO response = new QuizResponseDTO();
		response.setQuiz(quiz);
		List<QuizResponseDTO> quizzesList = new ArrayList<QuizResponseDTO>();
		quizzesList.add(response);
		when(quizService.findAllQuizzes()).thenReturn(quizzesList);
		
		this.mockMvc.perform(get("/quizzes"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andDo(document("quiz/find_all",preprocessResponse(prettyPrint())));
	}
	@Test
	public void testCreateQuiz() throws Exception
	{
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
		
		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuiz(quizObj);
		
		String quizJson = objMapper.writeValueAsString(quizDTO);
		
		when(quizService.createQuiz(Matchers.any(QuizDTO.class))).thenReturn(true);
		this.mockMvc.perform(post("/quiz/").contentType(MediaType.APPLICATION_JSON).content(quizJson))
		.andExpect(status().isOk())
		.andExpect(content().string(MessageConstant.SUCCESSFULLY_QUIZ_CREATED))
		.andDo(print())
		.andDo(document("quiz/create_quiz"));
	}
	@Test
	public void testPoolFindById() throws ServiceException
	{
		PoolResponseDTO poolDTO = new PoolResponseDTO();
		List<QuizPool> pools = new ArrayList<QuizPool>();
		QuizPool pool = new QuizPool();
		pool.setId(1);
		pool.setMaxNumerOfQuestion(2);
		pool.setName("java");
		pool.setQuizId(1);
		pools.add(pool);
		poolDTO.setPools(pools);
		when(quizService.findPoolsByQuizId(Matchers.anyInt())).thenReturn(poolDTO);
//		this.mockMvc.perform(get("pool/1"))
	}
}
