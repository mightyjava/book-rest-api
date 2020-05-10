package com.mightyjava.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -3101984166649269624L;

	public ApplicationException(String message) {
		super(message);
	}
}
