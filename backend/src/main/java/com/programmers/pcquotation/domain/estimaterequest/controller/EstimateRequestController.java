package com.programmers.pcquotation.domain.estimaterequest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.estimaterequest.dto.EstimateRequestResDto;
import com.programmers.pcquotation.domain.estimaterequest.service.EstimateRequestService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estimate/request")
@RequiredArgsConstructor
public class EstimateRequestController {
	private final EstimateRequestService estimateRequestService;

	record EstimateRequestData(@NotBlank String purpose, Integer budget, String otherRequest) {
	}

	@PostMapping
	public ResponseEntity<EstimateRequestData> createER(@RequestBody @Valid EstimateRequestData estimateRequestData,
		Principal principal) {
		Customer customer = estimateRequestService.findCustomer(principal.getName());
		estimateRequestService.createEstimateRequest(
			estimateRequestData.purpose,
			estimateRequestData.budget,
			estimateRequestData.otherRequest,
			customer);
		return new ResponseEntity<>(estimateRequestData, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EstimateRequestResDto>> getER() {
		//로그인 구현시 주석제거 해서 함수 변경
		//Customer customer = estimateRequestService.findCustomer(principal.getName());
		//List<EstimateRequest> estimateRequestList = estimateRequestService.getEstimateRequestByCustomerId(customer);
		List<EstimateRequestResDto> list = estimateRequestService.getAllEstimateRequest();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
