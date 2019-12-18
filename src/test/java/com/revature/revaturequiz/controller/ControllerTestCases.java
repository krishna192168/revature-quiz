package com.revature.revaturequiz.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;
import com.revature.revaturequiz.service.QuizService;
import com.revature.revaturequiz.util.MessageConstant;

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
				.apply(documentationConfiguration(this.restDoc).uris().withScheme("http").withHost("localhost").withPort(9001))
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
		
		quizObj.setId(14);
		quizObj.setName("java");
		quizObj.setTags("java,core java");
		quizObj.setActivityPoints(80);
		quizObj.setCreatedBy(1);
		quizObj.setDescription("java intro");
		quizObj.setIconUrl("https://start.atlassian.com/");
		quizObj.setIsShuffleAnswer(false);
		quizObj.setIsLevelOverride(false);
		quizObj.setIsDisplayScoreResult(false);
		quizObj.setIsQuizTimerEnable(true);
		quizObj.setSlugUrl("https://start.atlassian.com/");
		quizObj.setMetaKeywords("java");
		quizObj.setMetaDescription("java intro");
		quizObj.setQuizInstructions("quiz");
		quizObj.setPassPercentage(80);
		quizObj.setIsSlugUrlAccess(false);
		quizObj.setIsShuffleQuestion(false);
		quizObj.setIsSaveAndResume(false);
		quizObj.setModifiedBy(1);
		
		Date date = new Date();
		Long time = date.getTime();
		Timestamp currentTimestamp = new Timestamp(time);
		quizObj.setCreatedOn(currentTimestamp);
		quizObj.setMaxNumberOfAttempts(5);
		quizObj.setIsAttemptReview(false);
		quizObj.setIsShowWhetherCorrect(false);
		quizObj.setIsShowCorrectAnswer(false);
		quizObj.setIsShowAnswerExplanation(false);
		quizObj.setCategoryId(1);
		quizObj.setLevelId(1);
		
		QuizPool quizPool = new QuizPool();
		quizPool.setName("java pool");
		
		List<QuizPool> pools = new ArrayList<QuizPool>();
		pools.add(quizPool);
		
		QuizPoolQuestion poolQuestions = new QuizPoolQuestion();
		poolQuestions.setIsEvaluate(true);
		
		List<QuizPoolQuestion> questions = new ArrayList<QuizPoolQuestion>();
		questions.add(poolQuestions);
		
		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuiz(quizObj);
		quizDTO.setQuizPool(pools);
		quizDTO.setPoolQuestions(questions);
		
		String quizJson = objMapper.writeValueAsString(quizDTO);
		
		when(quizService.createQuiz(Matchers.any(QuizDTO.class))).thenReturn(true);
		this.mockMvc.perform(post("/quiz/").contentType(MediaType.APPLICATION_JSON).content(quizJson))
		.andExpect(status().isOk())
		.andExpect(content().string(MessageConstant.SUCCESSFULLY_QUIZ_CREATED))
		.andDo(print())
		.andDo(document("quiz/create_quiz",
				preprocessRequest(prettyPrint()),
				requestFields(
						//Id
						fieldWithPath("quiz.id")
						.description("auto generated id for quiz")
						.attributes(key("required").value(false)),
						//Name
						fieldWithPath("quiz.name")
						.description("name for quiz")
						.attributes(key("required").value(true)),
						//tags
						fieldWithPath("quiz.tags")
						.description("tags for quiz")
						.attributes(key("required").value(true)),
						//activityPoints
						fieldWithPath("quiz.activityPoints")
						.description("activityPoints for quiz")
						.attributes(key("required").value(false)),
						//duration
						fieldWithPath("quiz.duration")
						.description("duration for quiz")
						.attributes(key("required").value(false)),
						//maxNumberOfAttempts
						fieldWithPath("quiz.maxNumberOfAttempts")
						.description("duration for quiz")
						.attributes(key("required").value(false)),
						//isLevelOverride
						fieldWithPath("quiz.isLevelOverride")
						.description("isLevelOverride for quiz")
						.attributes(key("required").value(false)),
						//slugUrl
						fieldWithPath("quiz.slugUrl")
						.description("slugUrl for quiz")
						.attributes(key("required").value(false)),
						//description
						fieldWithPath("quiz.description")
						.description("description for quiz")
						.attributes(key("required").value(false)),
						//metaKeywords
						fieldWithPath("quiz.metaKeywords")
						.description("metaKeywords for quiz")
						.attributes(key("required").value(false)),
						//metaDescription
						fieldWithPath("quiz.metaDescription")
						.description("metaDescription for quiz")
						.attributes(key("required").value(false)),
						//iconUrl
						fieldWithPath("quiz.iconUrl")
						.description("iconUrl for quiz")
						.attributes(key("required").value(false)),
						//quizInstructions
						fieldWithPath("quiz.quizInstructions")
						.description("quizInstructions for quiz")
						.attributes(key("required").value(false)),
						//levelId
						fieldWithPath("quiz.levelId")
						.description("levelId for quiz")
						.attributes(key("required").value(true)),
						//categoryId
						fieldWithPath("quiz.categoryId")
						.description("categoryId for quiz")
						.attributes(key("required").value(true)),
						//passPercentage
						fieldWithPath("quiz.passPercentage")
						.description("passPercentage for quiz")
						.attributes(key("required").value(true)),
						//isSlugUrlAccess
						fieldWithPath("quiz.isSlugUrlAccess")
						.description("isSlugUrlAccess for quiz")
						.attributes(key("required").value(false)),
						//isQuizTimerEnable
						fieldWithPath("quiz.isQuizTimerEnable")
						.description("isQuizTimerEnable for quiz")
						.attributes(key("required").value(false)),
						//isShuffleQuestion
						fieldWithPath("quiz.isShuffleQuestion")
						.description("isShuffleQuestion for quiz")
						.attributes(key("required").value(false)),
						//isShuffleAnswer
						fieldWithPath("quiz.isShuffleAnswer")
						.description("isShuffleAnswer for quiz")
						.attributes(key("required").value(false)),
						//isDisplayScoreResult
						fieldWithPath("quiz.isDisplayScoreResult")
						.description("isDisplayScoreResult for quiz")
						.attributes(key("required").value(false)),
						//isAttemtReview
						fieldWithPath("quiz.isAttemptReview")
						.description("isAttemtReview for quiz")
						.attributes(key("required").value(false)),
						//isShowWhetherCorrect
						fieldWithPath("quiz.isShowWhetherCorrect")
						.description("isShowWhetherCorrect for quiz")
						.attributes(key("required").value(false)),
						//isShowCorrectAnswer
						fieldWithPath("quiz.isShowCorrectAnswer")
						.description("isShowCorrectAnswer for quiz")
						.attributes(key("required").value(false)),
						//isShowAnswerExplanation
						fieldWithPath("quiz.isShowAnswerExplanation")
						.description("isShowAnswerExplanation for quiz")
						.attributes(key("required").value(false)),
						//isSaveAndResume
						fieldWithPath("quiz.isSaveAndResume")
						.description("isSaveAndResume for quiz")
						.attributes(key("required").value(false)),
						//createOn
						fieldWithPath("quiz.createdOn")
						.description("createdOn for quiz")
						.attributes(key("required").value(false)),
						//modifiedOn
						fieldWithPath("quiz.modifiedOn")
						.description("modifiedOn for quiz")
						.attributes(key("required").value(false)),
						//createBy
						fieldWithPath("quiz.createdBy")
						.description("createdBy for quiz")
						.attributes(key("required").value(false)),
						//modifiedBy
						fieldWithPath("quiz.modifiedBy")
						.description("modifiedBy for quiz")
						.attributes(key("required").value(false)),
						//isStatus
						fieldWithPath("quiz.isStatus")
						.description("isStatus for quiz")
						.attributes(key("required").value(false)),
						/** Quiz pool **/
						//quizPoolId
						fieldWithPath("quizPool.[].id")
						.description("auto generated id for quiz pool")
						.attributes(key("required").value(false)),
						//poolPool name
						fieldWithPath("quizPool.[].name")
						.description("name for quiz pool")
						.attributes(key("required").value(true)),
						//poolPool maxNumerOfQuestion
						fieldWithPath("quizPool.[].maxNumerOfQuestion")
						.description("maxNumerOfQuestion for quiz pool")
						.attributes(key("required").value(false)),
						//poolPool quizId
						fieldWithPath("quizPool.[].quizId")
						.description("quizId for quiz pool")
						.attributes(key("required").value(false)),
						/** Pool questions **/
						//poolQuestionsId
						fieldWithPath("poolQuestions.[].id")
						.description("auto generated id for quiz pool questions")
						.attributes(key("required").value(false)),
						//questionId
						fieldWithPath("poolQuestions.[].questionId")
						.description("id for quiz pool questions")
						.attributes(key("required").value(false)),
						//quizPoolId
						fieldWithPath("poolQuestions.[].quizPoolId")
						.description("id for quiz pool questions")
						.attributes(key("required").value(false)),
						//quizPoolId
						fieldWithPath("poolQuestions.[].isSticky")
						.description("isSticky for quiz pool questions")
						.attributes(key("required").value(false)),
						//isEvaluate
						fieldWithPath("poolQuestions.[].isEvaluate")
						.description("isEvaluate for quiz pool questions")
						.attributes(key("required").value(false))
							)
					)
				);
	}
	@Test
	public void testPoolFindById() throws Exception
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
		this.mockMvc.perform(get("/pools/1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andDo(document("quiz/find_pools",
				preprocessResponse(prettyPrint()))
				)
			;
	}
}
