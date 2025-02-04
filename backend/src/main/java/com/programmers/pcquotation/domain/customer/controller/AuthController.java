package com.programmers.pcquotation.domain.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.customer.dto.SignupRequest;
import com.programmers.pcquotation.domain.customer.dto.SignupResponse;
import com.programmers.pcquotation.domain.customer.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/auth"))
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup/customer")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
		SignupResponse signupResponse = authService.addUser(signupRequest);
		return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
	}
}