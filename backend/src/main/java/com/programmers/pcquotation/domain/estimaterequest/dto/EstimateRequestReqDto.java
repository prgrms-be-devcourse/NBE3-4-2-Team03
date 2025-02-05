package com.programmers.pcquotation.domain.estimaterequest.dto;

import java.time.LocalDateTime;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;

import lombok.Getter;

@Getter
public class EstimateRequestReqDto {
	private Integer id;
	private String purpose;
	private Integer budget;
	private String otherRequest;
	LocalDateTime createDate;
	private Customer customer;

	public EstimateRequestReqDto(EstimateRequest estimateRequest) {
		this.id = estimateRequest.getId();
		this.purpose = estimateRequest.getPurpose();
		this.budget = estimateRequest.getBudget();
		this.otherRequest = estimateRequest.getOtherRequest();
		this.createDate = estimateRequest.getCreateDate();
		this.customer = estimateRequest.getCustomer();
	}
}
