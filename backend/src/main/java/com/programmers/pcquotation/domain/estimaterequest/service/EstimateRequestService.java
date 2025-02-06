package com.programmers.pcquotation.domain.estimaterequest.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;
import com.programmers.pcquotation.domain.estimaterequest.exception.NullEntityException;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.estimaterequest.repository.EstimateRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstimateRequestService {
	private final EstimateRequestRepository estimateRequestRepository;
	private final CustomerRepository customerRepository;

	public EstimateRequest createEstimateRequest(String purpose, Integer budget, String otherRequest, Customer customer) {
		return estimateRequestRepository.save(EstimateRequest
			.builder()
			.createDate(LocalDateTime.now())
			.purpose(purpose)
			.budget(budget)
			.otherRequest(otherRequest)
			.customer(customer)
			.build());
	}

	public Optional<EstimateRequest> getEstimateRequestById(Integer id) {
		return estimateRequestRepository.getEstimateRequestById(id);
	}
	public Customer findCustomer(String name){
        return customerRepository.getCustomerByUsername(name)
				.orElseThrow(NullEntityException::new);

	}

}
