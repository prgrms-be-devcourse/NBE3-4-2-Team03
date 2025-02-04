package com.programmers.pcquotation.estimaterequest.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.programmers.pcquotation.domain.estimaterequest.repository.EstimateRequestRepository;
import com.programmers.pcquotation.domain.estimaterequest.service.EstimateRequestService;

import jakarta.transaction.Transactional;

@SpringBootTest
class EstimateRequestServiceTest {

	@Autowired
	private EstimateRequestService estimateRequestService;
	@Autowired
	private EstimateRequestRepository estimateRequestRepository;

	@Test
	@Transactional
	@DisplayName("성공케이스 1")
	void 견적_요청_생성() {
		//given
		estimateRequestService.createEstimateRequest("asd", 1, "qwe");
		//when

		//then
		assertThat(estimateRequestRepository.findAll().size()).isEqualTo(1);
	}

	@Test
	@Transactional
	@DisplayName("성공케이스 2")
	void 견적_요청_생성2() {
		//given
		estimateRequestService.createEstimateRequest("asd", 1, "qwe");
		estimateRequestService.createEstimateRequest("asd", 2, "qwe");
		estimateRequestService.createEstimateRequest("asd", 3, "qwe");
		estimateRequestService.createEstimateRequest("asd", 4, "qwe");
		estimateRequestService.createEstimateRequest("asd", 5, "qwe");
		estimateRequestService.createEstimateRequest("asd", 6, "qwe");
		estimateRequestService.createEstimateRequest("asd", 7, "qwe");
		estimateRequestService.createEstimateRequest("asd", 8, "qwe");
		estimateRequestService.createEstimateRequest("asd", 9, "qwe");
		estimateRequestService.createEstimateRequest("asd", 10, "");
		//when

		//then
		assertThat(estimateRequestRepository.findAll().size()).isEqualTo(10);
	}
}