package com.programmers.pcquotation.domain.estimaterequest.controller;

import com.programmers.pcquotation.domain.customers.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.estimaterequest.service.EstimateRequestService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RestController
@RequestMapping("/estimate/request")
@RequiredArgsConstructor
public class EstimateRequestController {
	private final EstimateRequestService estimateRequestService;
	record EstimateRequestData(@NotBlank String purpose, @NotBlank Integer budget, String otherRequest){}

	@PostMapping
	public ResponseEntity<EstimateRequestData> createER(@RequestBody @Valid EstimateRequestData estimateRequestData, Principal principal){
		Customer customer =  estimateRequestService.findCustomer(principal.getName());
		estimateRequestService.createEstimateRequest(
			estimateRequestData.purpose,
			estimateRequestData.budget,
			estimateRequestData.otherRequest,
			customer);
		return new ResponseEntity<>(estimateRequestData, HttpStatus.CREATED);
	}
}
