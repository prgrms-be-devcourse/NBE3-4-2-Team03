package com.programmers.pcquotation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

        mvc.perform(
                post("/api/auth/signup/customer")
                        .content(signupRequest)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andExpect(status().isCreated());
    }
}