package com.programmers.pcquotation.estimaterequest.controller;

import com.programmers.pcquotation.estimaterequest.service.EstimateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estimate/request")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EstimateController {
    private final EstimateService estimateService;
    record EstimateRequestData(@NotBlank String purpose, Integer budget, String otherRequest){}

    @PostMapping
    public void createER(@RequestBody @Valid EstimateRequestData estimateRequestData){
        //principal을 사용해 구매자 아이디를 엔티티에 저장
        estimateService.createEstimateRequest(
                estimateRequestData.purpose,
                estimateRequestData.budget,
                estimateRequestData.otherRequest);
    }
}
