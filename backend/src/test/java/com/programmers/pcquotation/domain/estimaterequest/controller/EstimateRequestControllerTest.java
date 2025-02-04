package com.programmers.pcquotation.domain.estimaterequest.controller;

import com.programmers.pcquotation.domain.customers.entity.Customer;
import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.estimaterequest.exception.NullEntityException;
import com.programmers.pcquotation.domain.estimaterequest.service.EstimateRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EstimateRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstimateRequestService estimateRequestService;

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("성공케이스")
    public void 견적요청생성() throws Exception {
        //given
        Customer customer = new Customer();

        EstimateRequest estimateRequest = EstimateRequest
                .builder()
                .purpose("testPurpose")
                .budget(100)
                .otherRequest("testRequest")
                .customer(customer)
                .build();

        String data = """
                {
                    "purpose": "testPurpose",
                    "budget": 100,
                    "otherRequest": "testRequest"
                }
                """;

        //when
        when(estimateRequestService.createEstimateRequest(any(), any(), any(), any()))
                .thenReturn(estimateRequest);

        //then
        mockMvc.perform(
                        post("/estimate/request")
                                .content(data)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.purpose").value("testPurpose"))
                .andExpect(jsonPath("$.budget").value(100))
                .andExpect(jsonPath("$.otherRequest").value("testRequest"));
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("오류케이스")
    public void 견적요청생성실패() throws Exception {
        //given
        Customer customer = new Customer();

        EstimateRequest estimateRequest = EstimateRequest
                .builder()
                .purpose("testPurpose")
                .budget(100)
                .otherRequest("testRequest")
                .customer(customer)
                .build();

        String data = """
                {
                    "purpose": "sdf",
                    "budget": 100,
                    "otherRequest": "testRequest"
                }
                """;

        //when
        when(estimateRequestService.createEstimateRequest(any(), any(), any(), any()))
                .thenThrow(new NullEntityException());

        //then
        mockMvc.perform(
                        post("/estimate/request")
                                .content(data)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("입력한 내용을 다시 확인해주세요"));
    }
}
