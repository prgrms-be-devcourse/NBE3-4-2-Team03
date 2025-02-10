package com.programmers.pcquotation.domain.estimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmers.pcquotation.domain.estimate.entity.EstimateComponent;

public interface EstimateComponentRepository extends JpaRepository<EstimateComponent, Long> {
	@Modifying
	@Query("DELETE FROM EstimateComponent ec WHERE ec.item.id = :itemId")
	void deleteComponentsByItemId(@Param("itemId") Long itemId);
}

