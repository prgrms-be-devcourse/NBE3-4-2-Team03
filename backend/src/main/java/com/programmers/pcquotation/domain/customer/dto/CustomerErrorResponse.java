package com.programmers.pcquotation.domain.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class CustomerErrorResponse {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorCode;

	private String message;

	public CustomerErrorResponse(String message) {
		this.message = message;
	}
}