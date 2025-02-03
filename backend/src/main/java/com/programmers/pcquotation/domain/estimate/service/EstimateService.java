package com.programmers.pcquotation.domain.estimate.service;

import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import com.programmers.pcquotation.domain.estimate.repository.EstimateRepository;
import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstimateService {
    private final EstimateRepository estimateRepository;

    public List<Estimate> getEstimateList(){
        return estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimate(Integer id){
        return estimateRepository.findById(id);
    }

    public void create(Sellers sellers, EstimateRequest estimateRequest){
        Estimate.builder()
                .createDate(LocalDateTime.now())
                .sellers(sellers)
                .estimateRequest(estimateRequest)
                .build();
    }

    public void modify(Integer id){
        getEstimate(id).get();
    }

    public void delete(Integer id){
        estimateRepository.delete(getEstimate(id).get());
    }
}
