package com.programmers.pcquotation.estimatesquests.controller;

import com.programmers.pcquotation.estimatesquests.entity.EstimateRequest;
import com.programmers.pcquotation.estimatesquests.rsdata.RsData;
import com.programmers.pcquotation.estimatesquests.service.EstimateRequestService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estimate/request")
@RequiredArgsConstructor
public class EstimateRequestController {
    private final EstimateRequestService estimateRequestService;
    record EstimateRequestData(@NotBlank String purpose, @NotBlank Integer budget, @NotBlank String otherRequest){}

    @PostMapping
    @Transactional
    public ResponseEntity<RsData<Long>> create(@RequestBody @Valid EstimateRequestData estimateRequestData){
        EstimateRequest estimateRequest = estimateRequestService.createEstimateRequest(
                estimateRequestData.purpose,
                estimateRequestData.budget,
                estimateRequestData.otherRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RsData<>("200-1","%d번 글이 작성 되었습니다.".formatted(estimateRequest.getId())));
    }
}
