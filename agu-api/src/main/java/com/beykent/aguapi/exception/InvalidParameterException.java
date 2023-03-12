package com.beykent.aguapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends RuntimeException{

	private static final long serialVersionUID = -9037615351793682666L;
	
	private String message;
	private int statusCode;
	
	public InvalidParameterException(String message) {
		this.message = message;
		this.statusCode = HttpStatus.BAD_REQUEST.value();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
