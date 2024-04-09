package com.varsha.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> validationExceptions(MethodArgumentNotValidException ex){
		return new ResponseEntity<>("Give Username and Password in proper-format", HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handleValidationException(CustomerNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
	}
}
