package com.revature.revaturequiz.validator;

import javax.validation.ValidationException;

import org.springframework.util.StringUtils;

import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.exception.ValidatorException;
import com.revature.revaturequiz.util.MessageConstant;


public class QuizValidator {
	public static void quizValidator(QuizDTO quiz) throws ValidatorException
	{
		if(StringUtils.isEmpty(quiz.getQuiz().getName()))
		{
			throw new ValidationException(MessageConstant.INAVALID_QUIZ_NAME);
		}
		if(StringUtils.isEmpty(quiz.getQuiz().getTags()))
		{
			throw new ValidationException(MessageConstant.INAVALID_TAG);
		}
		if(quiz.getQuiz().getActivityPoints() < 0)
		{
			throw new ValidationException(MessageConstant.INAVALID_ACTIVITY_POINTS);
		}
		if(quiz.getQuiz().getMaxNumbetOfAttempts() < 0)
		{
			throw new ValidationException(MessageConstant.INAVALID_MAX_NO_ATTEMPTS);
		}
		if(quiz.getQuiz().getIsAttemptReview() == false && 
				quiz.getQuiz().getIsShowWhetherCorrect() == true
				)
		{
			throw new ValidationException(MessageConstant.INVALID_IS_SHOW_WHETHER_CORRECT);
		}
		if(
				quiz.getQuiz().getIsAttemptReview() == false &&
				quiz.getQuiz().getIsShowCorrectAnswer() == true
				)
		{
			throw new ValidatorException(MessageConstant.INVALID_IS_SHOW_CORRECT_ANSWER);
		}
//		if(quiz.getQuiz().getIsAttemptReview() == false || quiz.getQuiz().getIsShowWhetherCorrect() == false && quiz.getQuiz().getIsShowCorrectAnswer() == true )
//		{
//			throw new ValidatorException(MessageConstant.INVALID_IS_ANSWER_EXPLANATION);
//		}
	}
}
