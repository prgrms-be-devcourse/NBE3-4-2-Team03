package com.programmers.pcquotation.customers.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import com.programmers.pcquotation.domain.customers.dto.SignupRequest;
import com.programmers.pcquotation.domain.customers.dto.SignupResponse;
import com.programmers.pcquotation.domain.customers.exception.CustomerAlreadyExistException;
import com.programmers.pcquotation.domain.customers.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.customers.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@EnableTransactionManagement
@Transactional
public class CustomerAuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AuthService authService;

    @Test
    public void signup_success() throws Exception {
        String signupRequest = """
                {
                    "username": "user1",
                    "password": "1234",
                    "confirmPassword": "1234",
                    "customerName": "홍길동",
                    "email": "test@test.com",
                    "verificationQuestion": "가장 좋아하는 음식은",
                    "verificationAnswer": "밥"
                }
                """;

        SignupResponse mockResponse = SignupResponse.builder()
                .id(1L)
                .username("user1")
                .customerName("홍길동")
                .email("test@test.com")
                .build();

        when(authService.addUser(any(SignupRequest.class))).thenReturn(mockResponse);

        mvc.perform(
                post("/api/auth/signup/customer")
                        .content(signupRequest)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.customerName").value("홍길동"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void signup_passwordMismatch() throws Exception {
        String signupRequest = """
                {
                    "username": "user1",
                    "password": "1234",
                    "confirmPassword": "1111",
                    "customerName": "홍길동",
                    "email": "test@test.com",
                    "verificationQuestion": "가장 좋아하는 음식은",
                    "verificationAnswer": "밥"
                }
                """;

        when(authService.addUser(any(SignupRequest.class))).thenThrow(new PasswordMismatchException());

        mvc.perform(
                post("/api/auth/signup/customer")
                        .content(signupRequest)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password mismatch"));
    }

    @Test
    public void signup_customerAlreadyExist() throws Exception {
        String signupRequest = """
                {
                    "username": "user1",
                    "password": "1234",
                    "confirmPassword": "1234",
                    "customerName": "홍길동",
                    "email": "test@test.com",
                    "verificationQuestion": "가장 좋아하는 음식은",
                    "verificationAnswer": "밥"
                }
                """;

        when(authService.addUser(any(SignupRequest.class))).thenThrow(new CustomerAlreadyExistException());

        mvc.perform(
                        post("/api/auth/signup/customer")
                                .content(signupRequest)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User already exists"));
    }
}