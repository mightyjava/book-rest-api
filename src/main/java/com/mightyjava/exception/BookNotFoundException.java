package com.mightyjava.exception;

public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2533194229906054487L;
	
	public BookNotFoundException(String message) {
		super(message);
	}

}
