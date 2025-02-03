package com.programmers.pcquotation.domain.customers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.customers.dto.SignupRequest;
import com.programmers.pcquotation.domain.customers.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/auth"))
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup/customer")
	public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
		authService.addUser(signupRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}