package com.programmers.pcquotation.estimatesquests.service;

import com.programmers.pcquotation.estimatesquests.entity.EstimateRequest;
import com.programmers.pcquotation.estimatesquests.repository.EstimateRequestRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstimateRequestService {
    private final EstimateRequestRepository estimateRequestRepository;

    public Integer findId(Integer id){
        return estimateRequestRepository.findById(id).get().getId();
    }

    public EstimateRequest createEstimateRequest(@NotBlank String purpose, @NotBlank Integer budget, @NotBlank String otherRequest){
        return estimateRequestRepository.save(EstimateRequest
                .builder()
                        .purpose(purpose)
                        .budget(budget)
                        .otherRequests(otherRequest)
                        .createDate(LocalDateTime.now())
                .build());
    }

}
