package com.revature.revaturequiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;
import com.revature.revaturequiz.util.ConnectionUtil;
import com.revature.revaturequiz.util.MessageConstant;

@Repository
public class QuizDAOImpl implements QuizDAO {
	@Autowired
	private DataSource dataSource;
	
	Logger quizLogger = LoggerFactory.getLogger("QuizDAO");
	
	public List<Quiz> findAllQuizzes() throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<Quiz> quizzes = null;

		try {
			conn = dataSource.getConnection();
			quizzes = new ArrayList<Quiz>();
			String sqlStmt = "SELECT quiz.id,"
					+ "quiz.name AS quiz_name,"
					+ "quiz.tags,"
					+ "quiz.activity_point,"
					+ "quiz.duration,"
					+ "quiz.max_number_of_attempts,"
					+ "quiz.is_level_override,"
					+ "quiz.slug_url,"
					+ "quiz.description,"
					+ "quiz.meta_keywords,"
					+ "quiz.meta_description,"
					+ "quiz.icon_url,"
					+ "quiz.quiz_instructions,"
					+ "quiz.level_id,"
					+ "quiz.category_id,"
					+ "quiz.pass_percentage,"
					+ "quiz.is_slug_url_access,"
					+ "quiz.quiz_timer,"
					+ "quiz.shuffle_question,"
					+ "quiz.shuffle_answer,"
					+ "quiz.display_score_result,"
					+ "quiz.attempt_review,"
					+ "quiz.show_whether_correct,"
					+ "quiz.show_correct_answer,"
					+ "quiz.show_answer_explanation,"
					+ "quiz.is_save_and_resume,"
					+ "quiz.created_on,"
//					+ "quiz.updated_on,"
					+ "quiz.created_by,"
					+ "quiz.modified_by "
					+ "FROM quizzes quiz ORDER BY quiz.created_on LIMIT 5";
			pstmt = conn.prepareStatement(sqlStmt);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				quizzes.add(toRow(resultSet));
			}
		} catch (SQLException e) {
			quizLogger.error(e.getMessage(), e);
			throw new DBException(MessageConstant.UNABLE_TO_GET_QUIZZES);
		} finally {
			ConnectionUtil.close(conn, pstmt, resultSet);
		}

		return quizzes;
	}
	
	public Quiz toRow(ResultSet resultSet)
	{
		Quiz quiz = null;
		try {
			Integer quizId = resultSet.getInt("id");
			String quizName = resultSet.getString("quiz_name");
			String quizTags = resultSet.getString("tags");
			Integer activityPoints = resultSet.getInt("activity_point");
			Time duration = resultSet.getTime("duration"); 
			Integer maxNumberOfAttempts = resultSet.getInt("max_number_of_attempts");
			Boolean isLevelOverride = resultSet.getBoolean("is_level_override");
			String slugUrl = resultSet.getString("slug_url");
			String description = resultSet.getString("description");
			String metaKeywords = resultSet.getString("meta_keywords");
			String metaDescription = resultSet.getString("meta_description");
			String iconUrl = resultSet.getString("icon_url");
			String quizInstructions = resultSet.getString("quiz_instructions");
			Integer levelId = resultSet.getInt("level_id");
			Integer categoryId = resultSet.getInt("category_id");
			Integer passPercentage = resultSet.getInt("pass_percentage");
			Boolean isSlugUrlAccess = resultSet.getBoolean("is_slug_url_access");
			Boolean isQuizTimerEnnabled = resultSet.getBoolean("quiz_timer");
			Boolean isShuffleQuestion = resultSet.getBoolean("shuffle_question");
			Boolean isShuffleAnswer = resultSet.getBoolean("shuffle_answer");
			Boolean isDisplayScoreResult = resultSet.getBoolean("display_score_result");
			Boolean isAttemptReview = resultSet.getBoolean("attempt_review");
			Boolean isShowWhetherCorrect = resultSet.getBoolean("show_whether_correct");
			Boolean isShowCorrectAnswer = resultSet.getBoolean("show_correct_answer");
			Boolean isShowAnswerExplanation = resultSet.getBoolean("show_answer_explanation");
			Boolean isSaveAndResume = resultSet.getBoolean("is_save_and_resume");
			Timestamp createdOn = resultSet.getTimestamp("created_on");
//			Timestamp modifiedOn = resultSet.getTimestamp("updated_on");
			String createdBy = resultSet.getString("created_by");
			String modifiedBy = resultSet.getString("modified_by");

			quiz = new Quiz();
			quiz.setId(quizId);
			quiz.setName(quizName);
			quiz.setTags(quizTags);
			quiz.setActivityPoints(activityPoints);
			quiz.setDuration(duration);
			quiz.setMaxNumbetOfAttempts(maxNumberOfAttempts);
			quiz.setIsLevelOverride(isLevelOverride);
			quiz.setSlugUrl(slugUrl);
			quiz.setDescription(description);
			quiz.setMetaKeywords(metaKeywords);
			quiz.setMetaDescription(metaDescription);
			quiz.setIconUrl(iconUrl);
			quiz.setQuizInstructions(quizInstructions);
			quiz.setLevelId(levelId);
			quiz.setCategoryId(categoryId);
			quiz.setPassPercentage(passPercentage);
			quiz.setIsSlugUrlAccess(isSlugUrlAccess);
			quiz.setIsQuizTimerEnnable(isQuizTimerEnnabled);
			quiz.setIsShuffleQuestion(isShuffleQuestion);
			quiz.setIsShuffleAnswer(isShuffleAnswer);
			quiz.setIsDisplayScoreResult(isDisplayScoreResult);
			quiz.setIsAttemptReview(isAttemptReview);
			quiz.setIsShowWhetherCorrect(isShowWhetherCorrect);
			quiz.setIsShowCorrectAnswer(isShowCorrectAnswer);
			quiz.setIsShowAnswerExplanation(isShowAnswerExplanation);
			quiz.setIsSaveAndResume(isSaveAndResume);
			quiz.setCreatedOn(createdOn);
//			quiz.setModifiedOn(modifiedOn);
			quiz.setCreatedBy(createdBy);
			quiz.setModifiedBy(modifiedBy);
			
		} catch(SQLException e) {
			quizLogger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return quiz;
	}
	/**
	 * Find all quiz pools
	 * @throws DBException 
	 * */
	public List<QuizPool> findPoolsByQuizId(int quizId) throws DBException
	{
		List<QuizPool> quizPool = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			String sqlStmt = "SELECT id,name,max_number_of_questions,quiz_id FROM quiz_pools WHERE quiz_id = ? LIMIT 5";
			pstmt = conn.prepareStatement(sqlStmt);
			pstmt.setInt(1, quizId);
			resultSet = pstmt.executeQuery();
			quizPool = new ArrayList<QuizPool>();
			QuizPool quizPoolObj = null;
			while(resultSet.next())
			{
				quizPoolObj = new QuizPool();
				quizPoolObj.setId(resultSet.getInt("id"));
				quizPoolObj.setName(resultSet.getString("name"));
				quizPoolObj.setMaxNumerOfQuestion(resultSet.getInt("max_number_of_questions"));
				quizPoolObj.setQuizId(resultSet.getInt("quiz_id"));
				quizPool.add(quizPoolObj);
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new DBException(MessageConstant.UNABLE_TO_GET_QUIZ_POOL);
		}finally {
			ConnectionUtil.close(conn,pstmt,resultSet);
		}
		return quizPool;
	}
	
	public List<QuizPoolQuestion> findPoolQuestions(int poolId) throws DBException
	{
		List<QuizPoolQuestion> poolQuestions = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Connection conn = null;
		try {
				conn = dataSource.getConnection();
				String sqlStmt = "SELECT id,question_id,quiz_pool_id,is_sticky,is_evaluate FROM quiz_pool_questions WHERE quiz_pool_id = ? LIMIT 5";
				pstmt = conn.prepareStatement(sqlStmt);
				pstmt.setInt(1, poolId);
				resultSet = pstmt.executeQuery();
				QuizPoolQuestion questions = null;
				poolQuestions = new ArrayList<QuizPoolQuestion>();
			while(resultSet.next())
			{
				questions = new QuizPoolQuestion();
				questions.setId(resultSet.getInt("id"));
				questions.setQuestionId(resultSet.getInt("question_id"));
				questions.setQuizPoolId(resultSet.getInt("quiz_pool_id"));
				questions.setIsSticky(resultSet.getBoolean("is_sticky"));
				questions.setIsEvaluate(resultSet.getBoolean("is_evaluate"));
				poolQuestions.add(questions);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
			throw new DBException(MessageConstant.UNABLE_TO_GET_POOLS);
		}finally {
			ConnectionUtil.close(conn,pstmt,resultSet);
		}
		return poolQuestions;
	}
	
	/**
	 * Create create quiz
	 * @param: QuizDTO=>Quiz details.
	 * @return: if query successfully created return true or false.
	 */
	public Boolean createQuiz(QuizDTO quiz) throws DBException
	{
		Connection conn = null;
		PreparedStatement pstmt;
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
					+ "created_on,"
					+ "quiz_instructions"
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			pstmt.setString(28, quiz.getQuiz().getQuizInstructions());
			rowsAffectedQuiz = pstmt.executeUpdate();
			pstmt.close();
			//Get quiz last record id
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
			//Create pools
			List<QuizPool> quizPools = quiz.getQuizPool();
//			String sqlStmtPools = "INSERT INTO quiz_pools(name,max_number_of_questions,quiz_id)";
//			StringBuilder poolValues = new StringBuilder();
//			quizPools.forEach(
//						(pool) -> {
//									System.out.println(pool);
//										poolValues.append("VALUES(").
//										append(pool.getName()).append(",")
//										.append(pool.getMaxNumerOfQuestion()).append(",")
//										.append(quizId);
//									}
//					);
//			quizPools.forEach(
//						(pool) -> {
//							String sqlStmtPool = "INSERT INTO quiz_pools("
//									+ "name,"
//									+ "max_number_of_questions,"
//									+ "quiz_id"
//									+ ")"
//									+ "VALUES(?,?,?)";
//							try {
//								pstmt = conn.prepareStatement(sqlStmtPool);
//								pstmt.setString(1, pool.getName());
//								pstmt.setInt(2, pool.getMaxNumerOfQuestion());
//								pstmt.setInt(3, quizId);
//								pstmt.executeUpdate();
//								pstmt.close();
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//							
//						}
//					);
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
			//Get quiz pool last record id
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
			//Here change record has permanent
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
				e.printStackTrace();
			}
			throw new DBException(MessageConstant.UNABLE_TO_CREATE_QUIZ);
		}
		finally {
			ConnectionUtil.close(conn);
		}
		return isRowsInserted;
	}
	//Validate quiz
//	public void 
}
