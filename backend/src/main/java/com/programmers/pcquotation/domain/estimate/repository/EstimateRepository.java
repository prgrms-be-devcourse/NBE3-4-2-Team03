package com.programmers.pcquotation.domain.estimate.repository;

import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
}
