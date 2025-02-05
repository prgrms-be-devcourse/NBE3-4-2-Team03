package com.programmers.pcquotation.domain.seller.controller;

import java.util.NoSuchElementException;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.programmers.pcquotation.domain.seller.dto.ResponseSellerDto;
import com.programmers.pcquotation.domain.seller.dto.SellerRegisterDto;
import com.programmers.pcquotation.domain.seller.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.BusinessConfirmationService;
import com.programmers.pcquotation.domain.seller.service.SellerService;
import com.programmers.pcquotation.global.rq.Rq;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
	private final SellerService sellerService;
	private final BusinessConfirmationService businessConfirmationService;
	private final Rq rq;

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseSellerDto info() {
		Seller sellers = rq.getMember();
		if (sellers == null)
			throw new NullPointerException("존재하지 않는 사용자입니다.");
		return ResponseSellerDto.builder()
			.id(sellers.getId())
			.username(sellers.getUsername())
			.companyName(sellers.getCompanyName())
			.email(sellers.getEmail()).build();

	}
	@PostMapping
	public String create(@RequestBody @Valid SellerRegisterDto sellerRegisterDto) { //DTO 임시구현
		if(!sellerService.findByUserName(sellerRegisterDto.getUsername()).isEmpty())
			return "회원가입에 실패하였습니다.(아이디 중복)";

		sellerService.create(sellerRegisterDto);
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
	public String modify(@RequestBody @Valid SellerUpdateDto customerUpdateDto) {
		Seller seller = rq.getMember();
		if(seller == null)
			throw new NoSuchElementException("존재하지 않는 사용자입니다.");

		sellerService.modify(seller, customerUpdateDto);
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
	@Transactional(readOnly = true)
	public MemberLoginResBody login(@RequestBody @Valid MemberLoginReqBody reqBody) {
		Seller seller = sellerService.findByUserName(reqBody.username).
			orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
		if (!sellerService.matchPassword(seller,reqBody.password))
			throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");

		String accessToken = sellerService.getAccessToken(seller);
		rq.setCookie("accessToken", accessToken);
		rq.setCookie("apiKey", seller.getApiKey());
		return
			new MemberLoginResBody(
				seller.getApiKey(),
				accessToken,
				"로그인에 성공하였습니다."
			);
	}

	@GetMapping("/api/{code}")
	@Transactional(readOnly = true)
	public String checkCode(@PathVariable("code") String code) {
		Seller seller = rq.getMember();
		if(seller == null)
			throw new NoSuchElementException("존재하지 않는 사용자입니다.");

		if (businessConfirmationService.checkCode(code)) {
			sellerService.setIsVerified(seller,true);
			return "인증에 성공하였습니다.";
		}
		return "인증에 실패하였습니다.";
	}

}
