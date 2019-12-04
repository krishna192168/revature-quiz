package com.revature.revaturequiz.exception;

public class ServiceException extends Exception {
	/**
	 * Service Exception
	 */
	private static final long serialVersionUID = 1L;
	public ServiceException(String message)
	{
		super(message);
	}
	public ServiceException(String message,Throwable t)
	{
		super(message,t);
	}
}
