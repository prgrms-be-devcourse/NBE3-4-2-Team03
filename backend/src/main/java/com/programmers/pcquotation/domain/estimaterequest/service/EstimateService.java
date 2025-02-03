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
public class EstimateService {
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

	public Optional<Customer> findCustomer(String customer){
		return customerRepository.getCustomerByUsername(customer);
	}
}
