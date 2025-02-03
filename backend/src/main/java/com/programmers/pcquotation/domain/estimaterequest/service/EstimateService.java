package com.programmers.pcquotation.domain.estimaterequest.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.estimaterequest.repository.EstimateRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstimateService {
	private final EstimateRequestRepository estimateRequestRepository;

	public void createEstimateRequest(String purpose, Integer budget, String otherRequest) {
		estimateRequestRepository.save(EstimateRequest
			.builder()
			.createDate(LocalDateTime.now())
			.purpose(purpose)
			.budget(budget)
			.otherRequest(otherRequest)
			.build());
	}
}
