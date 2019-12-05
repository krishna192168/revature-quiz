package com.revature.revaturequiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

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
		ResultSet resultSet = null;
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
					+ "max_number_of_attempts,"
					+ "is_level_override,"
					+ "description,"
					+ "meta_keywords,"
					+ "meta_description,"
					+ "icon_url,"
					+ "duration,"
					+ "quiz_timer,"
					+ "shuffle_question,"
					+ "display_score_result,"
					+ "attempt_review,"
					+ "show_whether_correct,"
					+ "show_correct_answer,"
					+ "show_answer_explanation,"
					+ "is_save_and_resume,"
					+ "updated_on,"
					+ "modified_by,"
					+ "is_slug_url_access,"
					+ "created_on"
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sqlStmt);
			pstmt.setString(1, quiz.getQuiz().getName());
			pstmt.setString(2, quiz.getQuiz().getTags());
			pstmt.setInt(3, quiz.getQuiz().getActivityPoints());
			pstmt.setString(4, quiz.getQuiz().getSlugUrl());
			pstmt.setInt(5, quiz.getQuiz().getLevelId());
			pstmt.setInt(6, quiz.getQuiz().getCategoryId());
			pstmt.setInt(7, quiz.getQuiz().getPassPercentage());
			pstmt.setString(8, quiz.getQuiz().getCreatedBy());
			pstmt.setInt(9, quiz.getQuiz().getMaxNumbetOfAttempts());
			pstmt.setBoolean(10, quiz.getQuiz().getIsLevelOverride());
			pstmt.setString(11, quiz.getQuiz().getDescription());
			pstmt.setString(12, quiz.getQuiz().getMetaKeywords());
			pstmt.setString(13, quiz.getQuiz().getMetaDescription());
			pstmt.setString(14, quiz.getQuiz().getIconUrl());
			pstmt.setTime(15, quiz.getQuiz().getDuration());
			pstmt.setBoolean(16, quiz.getQuiz().getIsQuizTimerEnnable());
			pstmt.setBoolean(17, quiz.getQuiz().getIsShuffleQuestion());
			pstmt.setBoolean(18, quiz.getQuiz().getIsShuffleAnswer());
			pstmt.setBoolean(19, quiz.getQuiz().getIsDisplayScoreResult());
			pstmt.setBoolean(20, quiz.getQuiz().getIsAttemptReview());
			pstmt.setBoolean(21, quiz.getQuiz().getIsShowWhetherCorrect());
			pstmt.setBoolean(22, quiz.getQuiz().getIsShowCorrectAnswer());
			pstmt.setBoolean(23, quiz.getQuiz().getIsShowAnswerExplanation());
			pstmt.setBoolean(24, quiz.getQuiz().getIsSaveAndResume());
			pstmt.setTimestamp(25, quiz.getQuiz().getModifiedOn());
			pstmt.setBoolean(26, quiz.getQuiz().getIsSlugUrlAccess());
			//Get current time stamp
			Date date = new Date();
			Timestamp currentTimestamp = new Timestamp(date.getTime());
			pstmt.setTimestamp(27, currentTimestamp);
			rowsAffectedQuiz = pstmt.executeUpdate();
			pstmt.close();
			//Get last record id for quiz
			Integer quizId = null;
			String sqlStmtForId = "SELECT LAST_INSERT_ID() AS last_record_id";
			pstmt = conn.prepareStatement(sqlStmtForId);
			resultSet = pstmt.executeQuery();
			if(resultSet.next())
			{
				quizId = resultSet.getInt("last_record_id");
			}
			resultSet.close();
			pstmt.close();
			//Create pool
			List<QuizPool> quizPools = quiz.getQuizPool();
			for(QuizPool quizObj : quizPools)
			{
				String sqlStmtPool = "INSERT INTO quiz_pools("
						+ "name,"
						+ "max_number_of_questions,"
						+ "quiz_id"
						+ ")"
						+ "VALUES(?,?,?)";
				pstmt = conn.prepareStatement(sqlStmtPool);
				pstmt.setString(1, quizObj.getName());
				pstmt.setInt(2, quizObj.getMaxNumerOfQuestion());
				pstmt.setInt(3, quizId);
				rowsAffectedQuizPool = pstmt.executeUpdate();
				pstmt.close();
			}
			//Get last record id for quiz pool
			Integer quizPoolId = null;
			String sqlStmtForPoolId = "SELECT LAST_INSERT_ID() AS last_record_id";
			pstmt = conn.prepareStatement(sqlStmtForPoolId);
			resultSet = pstmt.executeQuery();
			if(resultSet.next())
			{
				quizPoolId = resultSet.getInt("last_record_id");
			}
			pstmt.close();
			resultSet.close();
			//Create pool question
			List<QuizPoolQuestion> poolQuestions = quiz.getPoolQuestions();
			for(QuizPoolQuestion poolQuestion : poolQuestions)
			{
				String sqlStmtPoolQuestion = "INSERT INTO quiz_pool_questions(question_id,quiz_pool_id,is_sticky,is_evaluate) VALUES(?,?,?,?)";
				pstmt = conn.prepareStatement(sqlStmtPoolQuestion);
				pstmt.setInt(1, poolQuestion.getQuestionId());
				pstmt.setInt(2, quizPoolId);
				pstmt.setBoolean(3, poolQuestion.getIsSticky());
				pstmt.setBoolean(4, poolQuestion.getIsEvaluate());
				rowsAffectedPoolQuestions = pstmt.executeUpdate();
				pstmt.close();
			}
			conn.commit();
			if(rowsAffectedQuiz == 1 && rowsAffectedPoolQuestions == 1 && rowsAffectedQuizPool == 1)
			{
				isRowsInserted = true;
			}
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
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return isRowsInserted;
	}
}
