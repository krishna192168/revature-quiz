package com.revature.revaturequiz.dao;

import java.util.List;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.exception.DBException;
import com.revature.revaturequiz.model.Quiz;
import com.revature.revaturequiz.model.QuizPool;
import com.revature.revaturequiz.model.QuizPoolQuestion;

public interface QuizDAO {
	public List<Quiz> findAllQuizzes() throws DBException;
	public Boolean createQuiz(QuizDTO quiz) throws DBException;
	public List<QuizPool> findPoolsByQuizId(int quizId) throws DBException;
	public List<QuizPoolQuestion> findPoolQuestions(int poolId) throws DBException;
}
