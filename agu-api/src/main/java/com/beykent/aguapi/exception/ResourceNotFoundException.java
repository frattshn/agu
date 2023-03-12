package com.beykent.aguapi.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 2764335167607738800L;
	
	private String message;
	private int statusCode;
	
	public ResourceNotFoundException(String message) {
		this.message = message;
		this.statusCode = HttpStatus.NOT_FOUND.value();
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
