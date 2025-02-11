package com.programmers.pcquotation.domain.delivery.controller;

import com.programmers.pcquotation.domain.delivery.entity.Delivery;
import com.programmers.pcquotation.domain.delivery.repository.DeliveryRepository;
import com.programmers.pcquotation.domain.delivery.service.DeliveryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(DeliveryController.class)
@Transactional
class DeliveryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DeliveryService deliveryService;

    @MockitoBean
    private DeliveryRepository deliveryRepository;


    @Test
    @WithMockUser(username = "user1")
    void 주문_내역_전체_조회() {

    }

    @Test
    @WithMockUser(username = "user1")
    void 주문_내역_단건_조회() {
    }

    @Test
    @WithMockUser(username = "user1")
    void 주문_생성() {
    }

    @Test
    @WithMockUser(username = "user1")
    void 주문_취소() {
    }

    @Test
    @WithMockUser(username = "user1")
    void 주소_수정() {
    }
}