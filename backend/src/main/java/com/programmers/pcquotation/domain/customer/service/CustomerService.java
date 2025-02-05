package com.programmers.pcquotation.domain.customer.service;

import java.util.Optional;

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

    public Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username);
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }
}