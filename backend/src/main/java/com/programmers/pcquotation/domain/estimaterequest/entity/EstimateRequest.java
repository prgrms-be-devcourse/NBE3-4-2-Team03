package com.programmers.pcquotation.domain.estimaterequest.entity;

import java.time.LocalDateTime;

import com.programmers.pcquotation.domain.customer.entity.Customer;

import com.programmers.pcquotation.domain.estimaterequest.dto.EstimateRequestData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 20)
	private String purpose;

	@Column(columnDefinition = "INTEGER")
	private Integer budget;

	@Column(length = 200)
	private String otherRequest;

	LocalDateTime createDate;

	@ManyToOne
	private Customer customer;

	public void UpdateEstimateRequest(EstimateRequestData estimateRequestData) {
		this.purpose = estimateRequestData.purpose();
		this.budget = estimateRequestData.budget();
		this.otherRequest = estimateRequestData.otherRequest();
	}
}
