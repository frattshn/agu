package com.beykent.aguapi.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 473014985662191980L;
	
	private String message;
	private int statusCode;
	
	public ResourceAlreadyExistException(String message) {
		this.message = message;
		this.statusCode = HttpStatus.CONFLICT.value();
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
