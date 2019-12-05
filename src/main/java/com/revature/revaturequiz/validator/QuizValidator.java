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
	}
}
