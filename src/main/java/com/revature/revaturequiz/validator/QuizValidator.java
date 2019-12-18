package com.revature.revaturequiz.validator;

import java.util.regex.Pattern;

import javax.validation.ValidationException;

import org.springframework.util.StringUtils;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.exception.ValidatorException;
import com.revature.revaturequiz.util.MessageConstant;


public class QuizValidator {
	private QuizValidator() {}
	public static void quizValidator(QuizDTO quiz) throws ValidatorException
	{
		if(StringUtils.isEmpty(quiz.getQuiz().getName()))
		{
			throw new ValidationException(MessageConstant.INAVALID_QUIZ_NAME);
		}
//		if(Pattern.matches("[a-zA-Z0-9]*", quiz.getQuiz().getName()))
//		{
//			throw new ValidatorException(MessageConstant.INAVALID_QUIZ_NAME);
//		}
		if(StringUtils.isEmpty(quiz.getQuiz().getTags()))
		{
			throw new ValidationException(MessageConstant.INAVALID_TAG);
		}
		if(StringUtils.isEmpty(quiz.getQuiz().getActivityPoints()) || quiz.getQuiz().getActivityPoints() < 0)
		{
			throw new ValidationException(MessageConstant.INAVALID_ACTIVITY_POINTS);
		}
		if(quiz.getQuiz().getMaxNumberOfAttempts() < 0)
		{
			throw new ValidationException(MessageConstant.INAVALID_MAX_NO_ATTEMPTS);
		}
		if(Boolean.FALSE.equals(quiz.getQuiz().getIsAttemptReview()) && 
				Boolean.TRUE.equals(quiz.getQuiz().getIsShowWhetherCorrect())
				)
		{
			throw new ValidationException(MessageConstant.INVALID_IS_SHOW_WHETHER_CORRECT);
		}
		if(quiz.getQuiz().getIsAttemptReview() == false && quiz.getQuiz().getIsShowCorrectAnswer() == true)
		{
			throw new ValidatorException(MessageConstant.INVALID_IS_SHOW_CORRECT_ANSWER);
		}
//		if(quiz.getQuiz().getIsAttemptReview() == false || quiz.getQuiz().getIsShowWhetherCorrect() == false && quiz.getQuiz().getIsShowCorrectAnswer() == true )
//		{
//			throw new ValidatorException(MessageConstant.INVALID_IS_ANSWER_EXPLANATION);
//		}
	}
}
