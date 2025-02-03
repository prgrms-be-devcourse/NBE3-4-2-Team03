package com.programmers.pcquotation.estimaterequest.repository;

import com.programmers.pcquotation.estimaterequest.entity.EstimateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Integer> {
}
