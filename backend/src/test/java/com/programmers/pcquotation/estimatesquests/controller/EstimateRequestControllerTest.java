package com.programmers.pcquotation.estimatesquests.controller;

import com.programmers.pcquotation.estimatesquests.entity.EstimateRequest;
import com.programmers.pcquotation.estimatesquests.service.EstimateRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EstimateRequestTest {

    @Autowired
    private EstimateRequestService estimateRequestService;

    @Test
    void 견적_요쳥_생성() {
        //given
        EstimateRequest estimateRequest = estimateRequestService.createEstimateRequest(
                "코딩공부", 1000, "");

        //when
        // 필요한 다른 로직을 추가합니다.

        //then
        assertThat(estimateRequest.getId()).isEqualTo(1);
    }
}
