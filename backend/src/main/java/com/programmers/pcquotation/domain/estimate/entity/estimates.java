package com.programmers.pcquotation.domain.estimate.entity;

import java.time.LocalDateTime;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class estimates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "Integer")
	private Integer totalPrice;

	@ManyToOne
	private EstimateRequest estimateRequest;

	private LocalDateTime createDate;

	@ManyToOne
	private Sellers sellers;

}
