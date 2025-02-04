package com.programmers.pcquotation.domain.estimaterequest.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.programmers.pcquotation.domain.customers.entity.Customer;
import com.programmers.pcquotation.domain.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.estimaterequest.repository.EstimateRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstimateRequestService {
	private final EstimateRequestRepository estimateRequestRepository;
	private final CustomerRepository customerRepository;

	public void createEstimateRequest(String purpose, Integer budget, String otherRequest, Customer customer) {
		estimateRequestRepository.save(EstimateRequest
			.builder()
			.createDate(LocalDateTime.now())
			.purpose(purpose)
			.budget(budget)
			.otherRequest(otherRequest)
			.customer(customer)
			.build());
	}

	public Customer findCustomer(String customer){
		return customerRepository.getCustomerByUsername(customer)
				.orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
	}
}
