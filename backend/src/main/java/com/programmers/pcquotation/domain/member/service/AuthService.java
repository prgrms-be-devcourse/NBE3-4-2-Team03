package com.programmers.pcquotation.domain.member.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.customer.dto.LoginRequest;
import com.programmers.pcquotation.domain.customer.dto.LoginResponse;
import com.programmers.pcquotation.domain.customer.dto.SignupRequest;
import com.programmers.pcquotation.domain.customer.dto.SignupResponse;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customer.exception.IncorrectLoginAttemptException;
import com.programmers.pcquotation.domain.customer.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customer.service.CustomerService;
import com.programmers.pcquotation.global.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final CustomerService customerService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public SignupResponse addUser(SignupRequest signupRequest) {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
			throw new PasswordMismatchException();
		}

		if (customerService.findCustomerByUsername(signupRequest.getUsername()) != null) {
			throw new CustomerAlreadyExistException();
		}

		if (customerService.findCustomerByEmail(signupRequest.getEmail()) != null) {
			throw new CustomerAlreadyExistException();
		}

		Customer customer = signupRequest.toCustomer();
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customerService.createCustomer(customer);

		return new SignupResponse(customer);
	}

	public LoginResponse processLogin(LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		Customer customer = customerService.findCustomerByUsername(username);

		if (customer == null) {
			throw new IncorrectLoginAttemptException();
		}

		if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
			throw new IncorrectLoginAttemptException();
		}

		setAuthentication(customer);

		return LoginResponse.builder()
				.accessToken(jwtUtil.generateToken(loginRequest.getUsername()))
				.expiresIn(jwtUtil.getAccessTokenExpirationSeconds())
				.build();
	}

	private void setAuthentication(Customer customer) {
		UserDetails user = new User(
				customer.getUsername(),
				null,
				customer.getAuthorities()
		);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				user,
				null,
				user.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}