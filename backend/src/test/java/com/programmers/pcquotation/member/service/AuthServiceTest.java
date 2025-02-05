package com.programmers.pcquotation.member.service;


import com.programmers.pcquotation.domain.customer.dto.LoginRequest;
import com.programmers.pcquotation.domain.customer.dto.LoginResponse;
import com.programmers.pcquotation.domain.customer.dto.SignupRequest;
import com.programmers.pcquotation.domain.customer.dto.SignupResponse;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customer.exception.IncorrectLoginAttemptException;
import com.programmers.pcquotation.domain.customer.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;
import com.programmers.pcquotation.domain.member.service.AuthService;
import com.programmers.pcquotation.global.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private JwtUtil jwtUtil;

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

    @Test
    public void signup_customerUserNameAlreadyExist() {
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
        when(customerRepository.getCustomerByUsername(signupRequest.getUsername())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExistException.class, () -> authService.processSignup(signupRequest));
    }

    @Test
    public void signup_customerEmailAlreadyExist() {
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
        when(customerRepository.getCustomerByEmail(signupRequest.getEmail())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExistException.class, () -> authService.processSignup(signupRequest));
    }

    @Test
    public void login_success() {
        LoginRequest loginRequest = new LoginRequest(
                "user1",
                "1234"
        );

        Customer customer = Customer.builder()
                .username("user1")
                .password("1234")
                .build();

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken("user1")).thenReturn("jwt.test.token");
        when(jwtUtil.getAccessTokenExpirationSeconds()).thenReturn(3600L);

        LoginResponse response = authService.processLogin(loginRequest);

        assertNotNull(response);
        assertEquals("jwt.test.token", response.getAccessToken());
        assertEquals(3600L, response.getExpiresIn());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(customer.getUsername(), authentication.getName());
        assertTrue(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
    }

    @Test
    public void login_usernameNotFound() {
        LoginRequest loginRequest = new LoginRequest(
                "user1",
                "1234"
        );

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.empty());

        assertThrows(IncorrectLoginAttemptException.class, () -> {
            authService.processLogin(loginRequest);
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }

    @Test
    public void login_incorrectPassword() {
        LoginRequest loginRequest = new LoginRequest(
                "user1",
                "1234"
        );

        Customer customer = Customer.builder()
                .username("user1")
                .password("1111")
                .build();

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.of(customer));

        assertThrows(IncorrectLoginAttemptException.class, () -> {
            authService.processLogin(loginRequest);
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }
}