package com.programmers.pcquotation.domain.estimaterequest.controller;

import java.security.Principal;
import java.util.List;

import com.programmers.pcquotation.domain.member.entitiy.Member;
import com.programmers.pcquotation.global.enums.UserType;
import com.programmers.pcquotation.global.rq.Rq;
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
	private final Rq rq;

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
	public ResponseEntity<List<EstimateRequestResDto>> getER(Principal principal) {
		String type = rq.getCookieValue("userType");
		UserType userType =  UserType.fromString(type);
		List<EstimateRequestResDto> list = null;

		switch (userType) {
			case Customer -> {
				Customer customer = estimateRequestService.findCustomer(principal.getName());
				list = estimateRequestService.getEstimateRequestByCustomerId(customer);
			}
			case Seller -> {
				list = estimateRequestService.getAllEstimateRequest();
			}
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}