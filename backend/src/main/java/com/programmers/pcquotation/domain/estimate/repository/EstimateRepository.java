package com.programmers.pcquotation.domain.estimate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.pcquotation.domain.estimate.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
	List<Estimate> getAllByEstimateRequest_Id(Integer estimateRequestId);

}

