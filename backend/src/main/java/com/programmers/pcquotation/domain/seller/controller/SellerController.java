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


import com.programmers.pcquotation.domain.seller.dto.SellerInfoRespnse;
import com.programmers.pcquotation.domain.seller.dto.SellerSignupRequest;
import com.programmers.pcquotation.domain.seller.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.BusinessConfirmationService;
import com.programmers.pcquotation.domain.seller.service.SellerService;
import com.programmers.pcquotation.global.enums.UserType;
import com.programmers.pcquotation.global.rq.Rq;

import jakarta.validation.Valid;
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
	public SellerInfoRespnse info() {
		Seller sellers = rq.getMember();
		if (sellers == null)
			throw new NullPointerException("존재하지 않는 사용자입니다.");
		return SellerInfoRespnse.builder()
			.id(sellers.getId())
			.username(sellers.getUsername())
			.companyName(sellers.getCompanyName())
			.email(sellers.getEmail()).build();

	}


	@PutMapping
	public String modify(@RequestBody @Valid SellerUpdateDto customerUpdateDto) {
		Seller seller = rq.getMember();
		if(seller == null)
			throw new NoSuchElementException("존재하지 않는 사용자입니다.");

		sellerService.modify(seller, customerUpdateDto);
		return "정보수정이 성공했습니다.";
	}

	@GetMapping("/business/{code}/check")
	@Transactional(readOnly = true)
	public String checkCode(@PathVariable("code") String code) {
		if (businessConfirmationService.checkCode(code)) {
			return "true";
		}
		return "false";
	}

}