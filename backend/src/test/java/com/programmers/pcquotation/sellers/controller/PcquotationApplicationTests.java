package com.programmers.pcquotation.sellers.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.pcquotation.domain.sellers.controller.SellersController;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import com.programmers.pcquotation.domain.sellers.service.SellersService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class PcquotationApplicationTests {
	@Autowired
	SellersService sellersService;

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("회원가입")
	void t1() throws Exception {
		ResultActions resultActions = mvc
			.perform(post("/sellers")
				.content("""
					{
					    "username": "test1234",
					    "password": "password1234",
					    "confirmPassword": "password1234",
					    "companyName": "너구리",
					    "email": "abc@gmail.com",
					    "verificationQuestion": "바나나는",
					    "verificationAnswer": "길어"
					}
					""".stripIndent())
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		Optional<Sellers> sellers = sellersService.findByName("test1234");

		resultActions
			.andExpect(handler().handlerType(SellersController.class))
			.andExpect(handler().methodName("create"))
			.andExpect(status().isOk())
			.andExpect(content().string("회원가입에 성공하였습니다."));

		assertNotNull(sellers.get());
		assertEquals(sellers.get().getUsername(), "test1234");
	}
	@Test
	@DisplayName("사업자 번호 조회")
	void t2() throws Exception {
		ResultActions resultActions1 = mvc
			.perform(get("/sellers/api/2208183676")
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		resultActions1
			.andExpect(handler().handlerType(SellersController.class))
			.andExpect(handler().methodName("checkCode"))
			.andExpect(status().isOk())
			.andExpect(content().string("인증에 성공하였습니다."));
		ResultActions resultActions2 = mvc
			.perform(get("/sellers/api/220818")
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		resultActions2
			.andExpect(handler().handlerType(SellersController.class))
			.andExpect(handler().methodName("checkCode"))
			.andExpect(status().isOk())
			.andExpect(content().string("인증에 실패하였습니다."));
	}

}