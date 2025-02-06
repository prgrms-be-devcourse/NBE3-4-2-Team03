package com.programmers.pcquotation.member.service;


import com.programmers.pcquotation.domain.customer.dto.CustomerLoginRequest;
import com.programmers.pcquotation.domain.customer.dto.CustomerLoginResponse;
import com.programmers.pcquotation.domain.customer.dto.CustomerSignupRequest;
import com.programmers.pcquotation.domain.customer.dto.CustomerSignupResponse;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customer.exception.IncorrectLoginAttemptException;
import com.programmers.pcquotation.domain.customer.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;
import com.programmers.pcquotation.domain.customer.service.CustomerService;
import com.programmers.pcquotation.domain.member.service.AuthService;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.SellerService;
import com.programmers.pcquotation.global.security.JwtUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

/*
    @Test
    public void signup_success() {
        CustomerSignupRequest customerSignupRequest = new CustomerSignupRequest(
                "user1",
                "1234",
                "1234",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        Customer customer = customerSignupRequest.toCustomer();
        when(customerRepository.getCustomerByUsername(customerSignupRequest.getUsername())).thenReturn(Optional.empty());
        when(customerRepository.getCustomerByEmail(customerSignupRequest.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        CustomerSignupResponse response = authService.processSignup(customerSignupRequest);

        assertNotNull(response);
        assertEquals("user1", response.getUsername());
        assertEquals("홍길동", response.getCustomerName());
        assertEquals("test@test.com", response.getEmail());
    }
*/
    @Test
    public void signup_passwordMismatch() {
        CustomerSignupRequest customerSignupRequest = new CustomerSignupRequest(
                "user1",
                "1234",
                "1111",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        assertThrows(PasswordMismatchException.class, () -> authService.processSignup(customerSignupRequest));
    }

    @Test
    public void signup_customerUserNameAlreadyExist() {
        CustomerSignupRequest customerSignupRequest = new CustomerSignupRequest(
                "user1",
                "1234",
                "1234",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        Customer customer = customerSignupRequest.toCustomer();
        when(customerRepository.getCustomerByUsername(customerSignupRequest.getUsername())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExistException.class, () -> authService.processSignup(customerSignupRequest));
    }

    @Test
    public void signup_customerEmailAlreadyExist() {
        CustomerSignupRequest customerSignupRequest = new CustomerSignupRequest(
                "user1",
                "1234",
                "1234",
                "홍길동",
                "test@test.com",
                "가장 좋아하는 음식은",
                "밥"
        );

        Customer customer = customerSignupRequest.toCustomer();
        when(customerRepository.getCustomerByEmail(customerSignupRequest.getEmail())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExistException.class, () -> authService.processSignup(customerSignupRequest));
    }

    @Test
	@WithMockUser(username = "user1", roles = {"CUSTOMER"}) //  SecurityContext 강제설정?
	public void login_success() {
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(
                "user1",
                "1234"
        );

        Customer customer = Customer.builder()
			.id(1L)
			.apiKey("1111")
                .username("user1")
                .password("1234")
                .build();

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(customerLoginRequest.getPassword(), customer.getPassword())).thenReturn(true);


        CustomerLoginResponse response = authService.processLogin(customerLoginRequest);

        assertNotNull(response);
        //assertEquals("jwt.test.token", response.getAccessToken());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(customer.getUsername(), authentication.getName());
        assertTrue(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
    }

    @Test
    public void login_usernameNotFound() {
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(
                "user1",
                "1234"
        );

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.empty());

        assertThrows(IncorrectLoginAttemptException.class, () -> {
            authService.processLogin(customerLoginRequest);
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }

    @Test
    public void login_incorrectPassword() {
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(
                "user1",
                "1234"
        );

        Customer customer = Customer.builder()
                .username("user1")
                .password("1111")
                .build();

        when(customerRepository.getCustomerByUsername("user1")).thenReturn(Optional.of(customer));

        assertThrows(IncorrectLoginAttemptException.class, () -> {
            authService.processLogin(customerLoginRequest);
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }
}