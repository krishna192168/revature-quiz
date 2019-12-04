package com.revature.revaturequiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;

@Repository
public class QuizDAOImpl implements QuizDAO {
	@Autowired
	private DataSource dataSource;
	public List<QuizResponseDTO> findAllQuizzes() throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<QuizResponseDTO> quizzes = null;

		try {
			conn = dataSource.getConnection();
			quizzes = new ArrayList<QuizResponseDTO>();
			String sqlStmt = "SELECT quiz.name AS quizname,level.name AS levelname,category.name AS categoryname,created_by,quiz.is_status "
					+ "FROM "
					+ "quizzes "
					+ "quiz,levels level,categories category "
					+ "ORDER BY quiz.created_on DESC;";
			pstmt = conn.prepareStatement(sqlStmt);
			resultSet = pstmt.executeQuery();
			QuizResponseDTO quizResponseDTO = null;
			while (resultSet.next()) {
				String quizName = resultSet.getString("quizname");
				String levelName = resultSet.getString("levelname");
				String categoryName = resultSet.getString("categoryname");
				String createdBy = resultSet.getString("created_by");
				Boolean isQuizStatus = resultSet.getBoolean("is_status");
				quizResponseDTO = new QuizResponseDTO();
				quizResponseDTO.setQuizName(quizName);
				quizResponseDTO.setLevelName(levelName);
				quizResponseDTO.setCategoryName(categoryName);
				quizResponseDTO.setCreatedBy(createdBy);
				quizResponseDTO.setIsStatus(isQuizStatus);
				quizzes.add(quizResponseDTO);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			try {
				conn.close();
				pstmt.close();
				resultSet.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}

		}

		return quizzes;
	}
	public Boolean createQuiz(QuizDTO quiz) throws DBException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		Boolean isRowsInserted = false;
		Integer rowsAffectedQuizPool = null;
		Integer rowsAffectedPoolQuestions = null;
		Integer rowsAffectedQuiz = null;
		Savepoint createQuiz = null;
		try {
			conn = dataSource.getConnection();
			//Set auto commit off
			conn.setAutoCommit(false);
			//Set save point
			createQuiz = conn.setSavepoint("CreateQuiz");
			String sqlStmt = "INSERT INTO quizzes("
					+ "name,"
					+ "tags,"
					+ "activity_point,"
					+ "slug_url,"
					+ "level_id,"
					+ "category_id,"
					+ "pass_percentage,"
					+ "created_by,"
//					+ "duration"
					+ "max_number_of_attempts,"
					+ "is_level_override,"
					+ "description"
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sqlStmt);
			pstmt.setString(1, quiz.getName());
			pstmt.setString(2, quiz.getTags());
			pstmt.setInt(3, quiz.getActivityPoints());
			pstmt.setString(4, quiz.getSlugUrl());
			pstmt.setInt(5, quiz.getLevelId());
			pstmt.setInt(6, quiz.getCategoryId());
			pstmt.setInt(7, quiz.getPassPercentage());
			pstmt.setString(8, quiz.getCreatedBy());
//			pstmt.setTime(9, );
			pstmt.setInt(9, quiz.getMaxNoOfAttempts());
			pstmt.setBoolean(10, quiz.getIsLevelOverride());
			pstmt.setString(11, quiz.getDescription());
			rowsAffectedQuiz = pstmt.executeUpdate();
			pstmt.close();
			//Create pool
			String sqlStmtPool = "INSERT INTO quiz_pools("
					+ "name,"
					+ "max_number_of_questions,"
					+ "quiz_id"
					+ ")"
					+ "VALUES(?,?,?)";
			pstmt = conn.prepareStatement(sqlStmtPool);
			pstmt.setString(1, quiz.getQuizPoolName());
			pstmt.setInt(2, quiz.getMaxNumberOfQuestions());
			pstmt.setInt(3, quiz.getQuizId());
			rowsAffectedQuizPool = pstmt.executeUpdate();
			pstmt.close();
			//Create pool question
			String sqlStmtPoolQuestion = "INSERT INTO quiz_pool_questions(question_id,quiz_pool_id,is_sticky,is_evaluate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sqlStmtPoolQuestion);
			pstmt.setInt(1, quiz.getQuestionId());
			pstmt.setInt(2, quiz.getQuizPoolId());
			pstmt.setBoolean(3, quiz.getIsSticky());
			pstmt.setBoolean(4, quiz.getIsEvaluate());
			rowsAffectedPoolQuestions = pstmt.executeUpdate();
			if(rowsAffectedPoolQuestions == 1 && rowsAffectedQuizPool == 1 && rowsAffectedQuiz == 1)
			{
				isRowsInserted = true;
			}
			conn.commit();
		}
		catch(SQLException e)
		{
			try {
				conn.rollback(createQuiz);
			} catch (SQLException e1) {
				System.err.println(e.getMessage());
			}
			throw new DBException(e.getMessage());
		}
		finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return isRowsInserted;
	}
}
