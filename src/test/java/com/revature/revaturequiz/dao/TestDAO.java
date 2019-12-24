package com.revature.revaturequiz.dao;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

@RunWith(MockitoJUnitRunner.class)
public class TestDAO {
	@InjectMocks
	private QuizDAOImpl quizDAO;
	
	@Mock
	private DataSource dataSource;
	
	@Mock
	private PreparedStatement pstmt;
	
	@Mock
	private Connection conn;
	
	@Mock
	private ResultSet resultSet;
	
	@Before
	public void setup() throws SQLException
	{
		when(dataSource.getConnection()).thenReturn(conn);
		when(conn.prepareStatement(Matchers.anyString())).thenReturn(pstmt);
		when(pstmt.executeQuery()).thenReturn(resultSet);
		when(pstmt.executeUpdate()).thenReturn(1);
		when(resultSet.getInt(Matchers.anyString())).thenReturn(1);
	}

	@Test
	public void testFindAllQuizzes() throws DBException, SQLException
	{
		when(resultSet.next()).thenReturn(Boolean.FALSE);
		Quiz quizObj = new Quiz();
		quizObj.setId(1);
		quizObj.setName("java");
		quizObj.setTags("java,core java");
//		when(quizDAO.toRow(resultSet)).thenReturn(quizObj);
		
		List<Quiz> quizzes = new ArrayList<Quiz>();
		
		quizzes = quizDAO.findAllQuizzes();
		System.err.println(quizzes);
		assertNotNull(quizzes);
	}
	@Test
	public void testFindPoolsByQuizId() throws DBException
	{
		List<QuizPool> quizPool = quizDAO.findPoolsByQuizId(1);
		assertNotNull(quizPool);
	}
	@Test
	public void testCreateQuiz() throws SQLException, DBException
	{
		when(pstmt.executeUpdate()).thenReturn(1);
		when(resultSet.next()).thenReturn(Boolean.TRUE);
		when(resultSet.getInt(Matchers.anyString())).thenReturn(1);
		//Set quiz details
		Quiz quizObj = new Quiz();
		quizObj.setId(1);
		quizObj.setName("nodejs1");
		quizObj.setTags("nodejs,core nodejs");
		quizObj.setActivityPoints(80);
		quizObj.setLevelId(1);
		quizObj.setCategoryId(1);
		quizObj.setPassPercentage(80);
		quizObj.setIsLevelOverride(false);
		quizObj.setMaxNumberOfAttempts(5);
		quizObj.setIsAttemptReview(false);
		quizObj.setIsShowWhetherCorrect(false);
		quizObj.setIsShowCorrectAnswer(false);
		quizObj.setIsShowAnswerExplanation(false);
		quizObj.setIsQuizTimerEnable(true);
		quizObj.setIsShuffleAnswer(false);
		quizObj.setIsShuffleQuestion(false);
		quizObj.setIsDisplayScoreResult(false);
		quizObj.setIsSaveAndResume(false);
		quizObj.setIsSlugUrlAccess(false);
		quizObj.setCreatedBy(1);
		quizObj.setModifiedBy(1);
		quizObj.setIsStatus(true);
		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuiz(quizObj);
		
		//Set pool details
		QuizPool pool = new QuizPool();
		pool.setMaxNumerOfQuestion(10);
		pool.setName("nodejs pool");
		pool.setQuizId(1);
		
		//Set pool questions
		QuizPoolQuestion poolQuestion = new QuizPoolQuestion();
		poolQuestion.setId(1);
		poolQuestion.setIsEvaluate(false);
		poolQuestion.setIsSticky(false);
		poolQuestion.setQuestionId(1);
		poolQuestion.setQuizPoolId(1);
		
		//Pool details
		List<QuizPool> quizPool = new ArrayList<>();
		quizPool.add(pool);
		
		//Quiz details
		List<QuizPoolQuestion> poolQuestions = new ArrayList<QuizPoolQuestion>();
		
		quizDTO.setQuizPool(quizPool);
		quizDTO.setPoolQuestions(poolQuestions);
		
		Boolean isQuizCreated = quizDAO.createQuiz(quizDTO);
		assertTrue(isQuizCreated);
	}
}
