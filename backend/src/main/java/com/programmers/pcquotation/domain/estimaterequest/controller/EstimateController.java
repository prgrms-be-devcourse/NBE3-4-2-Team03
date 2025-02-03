package com.programmers.pcquotation.domain.estimaterequest.controller;

import com.programmers.pcquotation.domain.customers.entity.Customer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.estimaterequest.service.EstimateService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RestController
@RequestMapping("/estimate/request")
@RequiredArgsConstructor
public class EstimateController {
	private final EstimateService estimateService;
	record EstimateRequestData(@NotBlank String purpose, @NotBlank Integer budget, String otherRequest){}

	@PostMapping
	public void createER(@RequestBody @Valid EstimateRequestData estimateRequestData, Principal principal) {
		Customer customer =  estimateService.findCustomer(principal.getName()).get();
		estimateService.createEstimateRequest(
			estimateRequestData.purpose,
			estimateRequestData.budget,
			estimateRequestData.otherRequest,
			customer);
	}
}
