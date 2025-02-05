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

import com.programmers.pcquotation.domain.seller.controller.SellerController;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.SellerService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class PcquotationApplicationTests {
	@Autowired
	SellerService sellerService;

	@Autowired
	private MockMvc mvc;

	String id = "test1234";
	String ps = "password1234";

	Seller register() throws Exception {
		ResultActions resultActions = mvc
			.perform(post("/seller")
				.content(String.format("""
					{
					    "username": "%s",
					    "password": "%s",
					    "confirmPassword": "password1234",
					    "companyName": "너구리",
					    "email": "abc@gmail.com",
					    "verificationQuestion": "바나나는",
					    "verificationAnswer": "길어"
					}
					""".stripIndent(),id,ps))
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		Optional<Seller> sellers = sellerService.findByName("test1234");
		assertNotNull(sellers.get());
		return sellers.get();
	}
	@Test
	@Transactional
	@DisplayName("회원가입")
	void t1() throws Exception {
		ResultActions resultActions = mvc
			.perform(post("/seller")
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
		Optional<Seller> sellers = sellerService.findByName("test1234");

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().string("회원가입에 성공하였습니다."));

		assertNotNull(sellers.get());
		assertEquals(sellers.get().getUsername(), "test1234");
	}

	@Test
	@Transactional
	@DisplayName("사업자 번호 조회")
	void t2() throws Exception {
		ResultActions resultActions1 = mvc
			.perform(get("/seller/api/2208183676")
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		resultActions1
			.andExpect(handler().methodName("checkCode"))
			.andExpect(status().isOk())
			.andExpect(content().string("인증에 성공하였습니다."));
		ResultActions resultActions2 = mvc
			.perform(get("/seller/api/220818")
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());
		resultActions2
			.andExpect(status().isOk())
			.andExpect(content().string("인증에 실패하였습니다."));
	}
	@Test
	@DisplayName("JWT 로그인 구현")
	void t3() throws Exception {
		register();
		ResultActions resultActions = mvc
			.perform(post("/seller/login")
				.content(String.format("""
                                {
                                    "username": "%s",
                                    "password": "%s"
                                }
                                """.stripIndent(),id,ps))
				.contentType(
					new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
				)
			)
			.andDo(print());

		resultActions
			.andExpect(handler().methodName("login"))
			.andExpect(status().isOk());

		String responseBody = resultActions.andReturn().getResponse().getContentAsString();

		String[] parts = responseBody.split(" ");
		assertEquals(2, parts.length, "응답 형식이 올바르지 않음");
		assertFalse(parts[0].isEmpty(), "JWT 토큰이 비어 있음");
		assertFalse(parts[1].isEmpty(), "API 키가 비어 있음");
	}
}