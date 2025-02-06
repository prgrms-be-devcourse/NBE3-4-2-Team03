package com.programmers.pcquotation.domain.member.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.customer.dto.CustomerLoginRequest;
import com.programmers.pcquotation.domain.customer.dto.CustomerLoginResponse;
import com.programmers.pcquotation.domain.customer.dto.CustomerSignupRequest;
import com.programmers.pcquotation.domain.customer.dto.CustomerSignupResponse;
import com.programmers.pcquotation.domain.member.service.AuthService;
import com.programmers.pcquotation.domain.seller.controller.SellerController;
import com.programmers.pcquotation.domain.seller.dto.SellerLoginRequest;
import com.programmers.pcquotation.domain.seller.dto.SellerLoginResponse;
import com.programmers.pcquotation.domain.seller.dto.SellerSignupRequest;
import com.programmers.pcquotation.domain.seller.dto.SellerSignupResponse;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup/customer")
    public ResponseEntity<CustomerSignupResponse> signup(@RequestBody CustomerSignupRequest customerSignupRequest) {
        CustomerSignupResponse signupResponse = authService.processSignup(customerSignupRequest);
        return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
    }
	@PostMapping("/signup/seller")
	public ResponseEntity<SellerSignupResponse> signup(@RequestBody SellerSignupRequest sellerSignupRequest) {
		SellerSignupResponse signupResponse = authService.processSignup(sellerSignupRequest);
		return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
	}
	@PostMapping("/login/customer")
	public ResponseEntity<CustomerLoginResponse> login(@RequestBody CustomerLoginRequest customerLoginRequest) {
        CustomerLoginResponse customerLoginResponse = authService.processLogin(customerLoginRequest);
		return new ResponseEntity<>(customerLoginResponse, HttpStatus.OK);
	}

	@PostMapping("/login/seller")
	public ResponseEntity<SellerLoginResponse> login(@RequestBody SellerLoginRequest sellerLoginRequest) {
		SellerLoginResponse sellerLoginResponse = authService.processLogin(sellerLoginRequest);
		return new ResponseEntity<>(sellerLoginResponse, HttpStatus.OK);
	}

}