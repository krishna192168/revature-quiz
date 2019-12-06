package com.revature.revaturequiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revaturequiz.dto.PoolResponseDTO;
import com.revature.revaturequiz.dto.QuizDTO;
import com.revature.revaturequiz.dto.QuizResponseDTO;
import com.revature.revaturequiz.exception.ServiceException;
import com.revature.revaturequiz.service.QuizService;
import com.revature.revaturequiz.util.MessageConstant;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	@Autowired
	private QuizService quizService;
	@GetMapping("/list")
	public ResponseEntity<Object> findAllQuizzes()
	{
		List<QuizResponseDTO> quizzesList = null;
		try {
			quizzesList = quizService.findAllQuizzes();
			return new ResponseEntity<>(quizzesList,HttpStatus.OK);
		}
		catch(ServiceException exception)
		{
			System.err.println(exception.getMessage());
			return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/")
	public ResponseEntity<Object> createQuiz(@RequestBody QuizDTO quiz)
	{
		 try {
			quizService.createQuiz(quiz);
			return new ResponseEntity<>(MessageConstant.SUCCESSFULLY_QUIZ_CREATED,HttpStatus.OK);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/{quizId}")
	public ResponseEntity<Object> findPoolByQuizId(@PathVariable("quizId") int quizId)
	{
		PoolResponseDTO poolResponse = null;
		
		poolResponse = quizService.findPoolByQuizId(quizId);
		
		return new ResponseEntity<>(poolResponse,HttpStatus.OK);
	}
}
