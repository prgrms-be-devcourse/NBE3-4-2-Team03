package com.programmers.pcquotation.estimaterequest.service;

import com.programmers.pcquotation.estimaterequest.repository.EstimateRequestRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EstimateServiceTest {

    @Autowired
    private EstimateService estimateService;
    @Autowired
    private EstimateRequestRepository estimateRequestRepository;

    @Test
    @Transactional
    @DisplayName("성공케이스 1")
    void 견적_요청_생성() {
        //given
        estimateService.createEstimateRequest("asd", 1, "qwe");
        //when

        //then
        assertThat(estimateRequestRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName("성공케이스 2")
    void 견적_요청_생성2() {
        //given
        estimateService.createEstimateRequest("asd", 1, "qwe");
        estimateService.createEstimateRequest("asd", 2, "qwe");
        estimateService.createEstimateRequest("asd", 3, "qwe");
        estimateService.createEstimateRequest("asd", 4, "qwe");
        estimateService.createEstimateRequest("asd", 5, "qwe");
        estimateService.createEstimateRequest("asd", 6, "qwe");
        estimateService.createEstimateRequest("asd", 7, "qwe");
        estimateService.createEstimateRequest("asd", 8, "qwe");
        estimateService.createEstimateRequest("asd", 9, "qwe");
        //when

        //then
        assertThat(estimateRequestRepository.findAll().size()).isEqualTo(9);
    }
}