package com.programmers.pcquotation.domain.estimate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmers.pcquotation.domain.estimate.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
	List<Estimate> getAllByEstimateRequest_Id(Integer estimateRequestId);

	@Modifying
	@Query("DELETE FROM EstimateComponent ec WHERE ec.item.id = :itemId")
	void deleteComponentsByItemId(@Param("itemId") Long itemId);
}

