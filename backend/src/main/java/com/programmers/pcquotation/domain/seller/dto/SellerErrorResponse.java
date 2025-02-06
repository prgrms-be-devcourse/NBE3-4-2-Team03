package com.programmers.pcquotation.domain.seller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class SellerErrorResponse {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorCode;

	private String message;

	public SellerErrorResponse(String message) {
		this.message = message;
	}
}