package com.programmers.pcquotation.domain.customers.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.customers.dto.SignupRequest;
import com.programmers.pcquotation.domain.customers.dto.SignupResponse;
import com.programmers.pcquotation.domain.customers.entity.Customer;
import com.programmers.pcquotation.domain.customers.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customers.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final CustomerRepository customerRepository;

    public SignupResponse addUser(SignupRequest signupRequest) {
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        if (customerRepository.getCustomerByUsername(signupRequest.getUsername()).isEmpty()) {
            throw new CustomerAlreadyExistException();
        }

        if (customerRepository.getCustomerByEmail(signupRequest.getEmail()).isEmpty()) {
            throw new CustomerAlreadyExistException();
        }

        Customer customer = signupRequest.toCustomer();
        customerRepository.save(customer);

        return new SignupResponse(customer);
    }
}