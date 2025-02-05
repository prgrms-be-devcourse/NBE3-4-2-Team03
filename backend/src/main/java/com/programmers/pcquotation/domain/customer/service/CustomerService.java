package com.programmers.pcquotation.domain.customer.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username)
                .orElse(null);
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email)
                .orElse(null);
    }
}