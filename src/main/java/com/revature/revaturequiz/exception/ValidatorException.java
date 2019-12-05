package com.revature.revaturequiz.exception;

public class ValidatorException extends Exception {
	private static final long serialVersionUID = 1L;
	public ValidatorException(String message) {
		super(message);
	}
	public ValidatorException(String message, Throwable t) {
		super(message,t);
	}
}
