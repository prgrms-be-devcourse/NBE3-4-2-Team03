package com.programmers.pcquotation.domain.sellers.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.customers.exception.PasswordMismatchException;
import com.programmers.pcquotation.domain.sellers.dto.ResponseSellerDto;
import com.programmers.pcquotation.domain.sellers.dto.SellerRegisterDto;
import com.programmers.pcquotation.domain.sellers.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import com.programmers.pcquotation.domain.sellers.service.BusinessConfirmationService;
import com.programmers.pcquotation.domain.sellers.service.SellersService;
import com.programmers.pcquotation.global.rq.Rq;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellersController {
	private final SellersService sellersService;
	private final BusinessConfirmationService businessConfirmationService;
	private final Rq rq;

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseSellerDto info() {//jwt 구현후 수정할것 DTO도 임시구현
		Sellers sellers = rq.getMember();
		if(sellers == null)
			throw new NullPointerException("존재하지 않는 사용자입니다.");
		return ResponseSellerDto.builder()
			.id(sellers.getId())
			.username(sellers.getUsername())
			.companyName(sellers.getCompanyName())
			.email(sellers.getEmail()).build();
	}

	@PostMapping
	public String create(@RequestBody @Valid SellerRegisterDto sellerRegisterDto) { //DTO 임시구현
		if(!sellersService.findByName(sellerRegisterDto.getUsername()).isEmpty())
			return "회원가입에 실패하였습니다.(아이디 중복)";
		sellersService.create(sellerRegisterDto);
		return "회원가입에 성공하였습니다.";
	}

	//뭐로 넘어오는지모르니 임시
	record MemberLoginReqBody(
		@NotBlank
		String username,

		@NotBlank
		String password
	) {
	}

	@PutMapping
	@Transactional(readOnly = true)
	public String modify(Principal principal, @RequestBody @Valid SellerUpdateDto customerUpdateDto) {
		Sellers sellers = sellersService.findByName(principal.getName()).
			orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

		sellersService.modify(sellers, customerUpdateDto);
		return "정보수정이 성공했습니다.";
	}

	record MemberLoginResBody(
		@NonNull
		String apiKey,
		@NonNull
		String accessToken,
		@NonNull
		String message
	) {}


	@PostMapping("/login")
	public MemberLoginResBody login(
		@RequestBody @Valid MemberLoginReqBody reqBody
	) {
		Sellers sellers = sellersService
			.findByName(reqBody.username)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
		if(!sellersService.matchPassword(sellers,reqBody.password))
			throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");

		String accessToken = sellersService.getAccessToken(sellers);
		rq.setCookie("accessToken", accessToken);
		rq.setCookie("apiKey", sellers.getApiKey());
		return
			new MemberLoginResBody(
				sellers.getApiKey(),
				accessToken,
				"로그인에 성공하였습니다."
			);
	}
	@GetMapping("/api/{code}")
	@Transactional(readOnly = true)
	public String checkCode(@PathVariable("code") String code) {
		if(businessConfirmationService.checkCode(code)){
			//계정 인증여부 수정
			return "인증에 성공하였습니다.";
		}
		return "인증에 실패하였습니다.";
	}

}
