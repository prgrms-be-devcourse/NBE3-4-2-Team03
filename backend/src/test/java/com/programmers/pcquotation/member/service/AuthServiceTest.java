package com.programmers.pcquotation.member.service;


import com.programmers.pcquotation.domain.customer.dto.SignupRequest;
import com.programmers.pcquotation.domain.customer.dto.SignupResponse;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;
import com.programmers.pcquotation.domain.member.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockitoBean
    private CustomerRepository customerRepository;

    @Test
    public void signup_success() {
        SignupRequest signupRequest = new SignupRequest(
                "user1",
                "1234",
                "1234",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        Customer customer = signupRequest.toCustomer();
        when(customerRepository.getCustomerByUsername(signupRequest.getUsername())).thenReturn(Optional.empty());
        when(customerRepository.getCustomerByEmail(signupRequest.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        SignupResponse response = authService.processSignup(signupRequest);

        assertNotNull(response);
        assertEquals("user1", response.getUsername());
        assertEquals("홍길동", response.getCustomerName());
        assertEquals("test@test.com", response.getEmail());
    }

    @Test
    public void signup_passwordMismatch() {
        SignupRequest signupRequest = new SignupRequest(
                "user1",
                "1234",
                "1111",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        assertThrows(PasswordMismatchException.class, () -> authService.processSignup(signupRequest));
    }
}