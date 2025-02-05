package com.programmers.pcquotation.domain.estimaterequest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Integer> {
	Optional<EstimateRequest> getEstimateRequestById(Integer id);

}
