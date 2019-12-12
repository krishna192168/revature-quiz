package com.revature.revaturequiz.dao;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		when(resultSet.next()).thenReturn(Boolean.FALSE);
	}

	@Test
	public void testFindAllQuizzes() throws DBException
	{
		quizDAO.findAllQuizzes();
	}
	@Test
	public void testFindPoolsByQuizId() throws DBException
	{
		quizDAO.findPoolsByQuizId(1);
	}
	@Test
	public void testCreateQuiz() throws SQLException, DBException
	{
		Quiz quizObj = new Quiz();
		quizObj.setId(1);
		quizObj.setName("java");
		quizObj.setTags("java,core java");
		quizObj.setActivityPoints(80);
		quizObj.setLevelId(1);
		quizObj.setCategoryId(1);
		quizObj.setPassPercentage(80);
		quizObj.setIsLevelOverride(false);
		quizObj.setMaxNumbetOfAttempts(5);
		quizObj.setIsAttemptReview(false);
		quizObj.setIsShowWhetherCorrect(false);
		quizObj.setIsShowCorrectAnswer(false);
		quizObj.setIsShowAnswerExplanation(false);
		quizObj.setIsQuizTimerEnnable(true);
		quizObj.setIsShuffleAnswer(false);
		quizObj.setIsShuffleQuestion(false);
		quizObj.setIsDisplayScoreResult(false);
		quizObj.setIsSaveAndResume(false);
		quizObj.setIsSlugUrlAccess(false);
		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuiz(quizObj);
		when(pstmt.executeUpdate()).thenReturn(1);
		
		quizDAO.createQuiz(quizDTO);
	}
}
