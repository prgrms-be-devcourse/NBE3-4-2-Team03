package com.programmers.pcquotation.domain.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.programmers.pcquotation.domain.customer.dto.CustomerErrorResponse;

@ControllerAdvice
public class CustomerExceptionHandler {
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<CustomerErrorResponse> handlePasswordMismatch(PasswordMismatchException ex) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(new CustomerErrorResponse("Password mismatch"));
	}

	@ExceptionHandler(CustomerAlreadyExistException.class)
	public ResponseEntity<CustomerErrorResponse> handleCustomerAlreadyExist(CustomerAlreadyExistException ex) {
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(new CustomerErrorResponse("User already exists"));
	}

	@ExceptionHandler(IncorrectLoginAttemptException.class)
	public ResponseEntity<CustomerErrorResponse> handleIncorrectLoginAttempt(IncorrectLoginAttemptException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new CustomerErrorResponse("Incorrect ID or Password"));
	}
}