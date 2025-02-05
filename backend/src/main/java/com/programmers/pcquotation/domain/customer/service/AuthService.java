package com.programmers.pcquotation.domain.customer.service;

import com.programmers.pcquotation.domain.customer.dto.LoginRequest;
import com.programmers.pcquotation.domain.customer.dto.LoginResponse;
import com.programmers.pcquotation.domain.customer.exception.WrongLoginAttemptException;
import com.programmers.pcquotation.global.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.customer.dto.SignupRequest;
import com.programmers.pcquotation.domain.customer.dto.SignupResponse;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customer.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public SignupResponse addUser(SignupRequest signupRequest) {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
			throw new PasswordMismatchException();
		}

		if (!customerRepository.getCustomerByUsername(signupRequest.getUsername()).isEmpty()) {
			throw new CustomerAlreadyExistException();
		}

		if (!customerRepository.getCustomerByEmail(signupRequest.getEmail()).isEmpty()) {
			throw new CustomerAlreadyExistException();
		}

		Customer customer = signupRequest.toCustomer();
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customerRepository.save(customer);

		return new SignupResponse(customer);
	}

	public LoginResponse processLogin(LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		Customer customer = customerRepository.getCustomerByUsername(username)
				.orElseThrow(WrongLoginAttemptException::new);

		if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
			throw new WrongLoginAttemptException();
		}

		return LoginResponse.builder()
				.accessToken(jwtUtil.generateToken(loginRequest.getUsername()))
				.expiresIn(jwtUtil.getAccessTokenExpirationSeconds())
				.build();
	}
}