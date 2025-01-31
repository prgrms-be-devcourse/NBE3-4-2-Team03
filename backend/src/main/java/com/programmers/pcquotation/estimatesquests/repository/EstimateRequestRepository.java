package com.programmers.pcquotation.estimatesquests.repository;

import com.programmers.pcquotation.estimatesquests.entity.EstimateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Integer> {
}
