package com.programmers.pcquotation.estimaterequest.service;

import com.programmers.pcquotation.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.estimaterequest.repository.EstimateRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstimateService {
    private final EstimateRequestRepository estimateRequestRepository;

    public void createEstimateRequest(String purpose, Integer budget, String otherRequest){
        estimateRequestRepository.save(EstimateRequest
                .builder()
                        .createDate(LocalDateTime.now())
                        .purpose(purpose)
                        .budget(budget)
                        .otherRequest(otherRequest)
                .build());
    }
}
