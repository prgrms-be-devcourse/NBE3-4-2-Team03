package com.programmers.pcquotation.estimate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.pcquotation.domain.estimate.controller.EstimateController;
import com.programmers.pcquotation.domain.seller.service.SellerService;
import com.programmers.pcquotation.util.util;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EstimateControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private SellerService sellerService;

	@Test
	@WithMockUser(username = "seller123", roles = {"SELLER"})
	@DisplayName("견적작성 테스트")
	void v1() throws Exception {
		String token  = "Bearer " + util.loginSeller("seller123","zzzzz",mvc,sellerService);
		String requestBody = """
			{
			   "estimateRequestId" : 1,
			   "sellerId" : "seller123",
			   "item" : [
			     {
			       "item" : 1,
			       "price" : 135000
			     },
			     {
			       "item" : 2,
			       "price" : 199000
			     }
			   ]
			 }
			""";

		ResultActions resultActions = mvc.perform(
			post("/api/estimate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.characterEncoding(StandardCharsets.UTF_8)
				.header("Authorization",  token)
		).andDo(print());

		// 응답 상태 코드 확인
		resultActions
			.andExpect(status().isOk());

		//테스트 추가 검증 필요
	}

	@Test
	@WithMockUser(username = "seller123", roles = {"SELLER"})
	@DisplayName("견적요청 별 견적작성 조회")
	void v2() throws Exception {
		String token  = "Bearer " + util.loginSeller("seller123","zzzzz",mvc,sellerService);

		ResultActions resultActions = mvc.perform(
			get("/api/estimate/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.header("Authorization",  token)

		).andDo(print());

		// 응답 상태 코드 확인
		resultActions
			.andExpect(handler().handlerType(EstimateController.class))
			.andExpect(handler().methodName("getEstimateByRequest"))
			.andExpect(status().isOk())
			// 응답 본문이 배열인지 확인
			.andExpect(jsonPath("$").isArray())
			// 응답의 각 필드 존재 여부 확인
			.andExpect(jsonPath("$[0].id").exists())
			.andExpect(jsonPath("$[0].seller").exists())
			.andExpect(jsonPath("$[0].date").exists())
			.andExpect(jsonPath("$[0].totalPrice").exists())
			.andExpect(jsonPath("$[0].items").exists());
	}

}