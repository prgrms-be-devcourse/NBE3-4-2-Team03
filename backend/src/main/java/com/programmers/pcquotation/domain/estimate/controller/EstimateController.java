package com.programmers.pcquotation.domain.estimate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.estimate.dto.EstimateCreateRequest;
import com.programmers.pcquotation.domain.estimate.dto.ReceivedQuoteDTO;
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

	@GetMapping("/api/estimates/{id}")
	public ResponseEntity<List<ReceivedQuoteDTO>> getEstimateByCustomer(@PathVariable Integer id) {
		List<ReceivedQuoteDTO> estimateByCustomer = estimateService.getEstimateByRequest(id);
		return new ResponseEntity<>(estimateByCustomer, HttpStatus.OK);
	}

}
