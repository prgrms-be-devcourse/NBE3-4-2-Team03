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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EstimateControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("견적작성 테스트")
	void createEstimate() throws Exception {
		String requestBody = """
			{
			   "estimateRequestId" : 1,
			   "sellerId" : "seller1",
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
			post("/api/estimates")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.characterEncoding(StandardCharsets.UTF_8)
		).andDo(print());

		// 응답 상태 코드 확인
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.estimateRequestId").value(1))
			.andExpect(jsonPath("$.sellerId").value("seller1"))
			.andExpect(jsonPath("$.item[0].item").value(1))
			.andExpect(jsonPath("$.item[0].price").value(135000))
			.andExpect(jsonPath("$.item[0].item").value(2))
			.andExpect(jsonPath("$.item[1].price").value(199000));

		//테스트 추가 검증 필요
	}

}