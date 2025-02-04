package com.programmers.pcquotation.domain.estimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.pcquotation.domain.estimate.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
}
