package com.programmers.pcquotation.domain.customer.dto;

import com.programmers.pcquotation.domain.customer.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupResponse {
	private final Long id;
	private final String username;
	private final String customerName;
	private final String email;

	public SignupResponse(Customer customer) {
		this.id = customer.getId();
		this.username = customer.getUsername();
		this.customerName = customer.getCustomerName();
		this.email = customer.getEmail();
	}
}