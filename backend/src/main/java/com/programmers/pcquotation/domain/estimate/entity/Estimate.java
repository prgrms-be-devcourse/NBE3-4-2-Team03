package com.programmers.pcquotation.domain.estimate.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private EstimateRequest estimateRequest;

	@ManyToOne
	private Seller seller;
	
	private Integer totalPrice;

	@OneToMany(mappedBy = "estimate")
	private List<EstimateComponent> estimateComponents = new ArrayList<>();

	private LocalDateTime createDate;

	@Builder
	public Estimate(EstimateRequest estimateRequest, Seller seller, Integer totalPrice,
		List<EstimateComponent> estimateComponents) {
		this.estimateRequest = estimateRequest;
		this.seller = seller;
		this.totalPrice = totalPrice;
		this.createDate = LocalDateTime.now();
		this.estimateComponents = estimateComponents;
	}

}
