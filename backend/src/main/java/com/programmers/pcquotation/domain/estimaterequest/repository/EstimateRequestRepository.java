package com.programmers.pcquotation.domain.estimaterequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Integer> {
}
