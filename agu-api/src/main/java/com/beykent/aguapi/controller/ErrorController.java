package com.beykent.aguapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.beykent.aguapi.exception.ResourceAlreadyExistsException;
import com.beykent.aguapi.exception.ResourceNotFoundException;
import com.beykent.aguapi.response.ErrorResponse;

public class ErrorController {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getStatusCode(), ex.getMessage()));
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadExistsException(ResourceAlreadyExistsException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getStatusCode(), ex.getMessage()));
	}
	
	

}
