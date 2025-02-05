package com.programmers.pcquotation.domain.estimate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.estimate.dto.EstimateCreateRequest;
import com.programmers.pcquotation.domain.estimate.service.EstimateService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EstimateController {
	private final EstimateService estimateService;

	@PostMapping("/api/estimates")
	public ResponseEntity<String> createEstimate(@RequestBody EstimateCreateRequest request) {
		estimateService.createEstimate(request);
		return ResponseEntity.ok().body("");
	}
}
