package com.programmers.pcquotation.sellers;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.sellers.DTO.CustomerUpdateDto;
import com.programmers.pcquotation.sellers.DTO.ResponseSellerDto;
import com.programmers.pcquotation.sellers.DTO.SellerRegisterDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellersController {
	private final SellersService sellersService;

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseSellerDto info(Principal principal) {//jwt 구현후 수정할것 DTO도 임시구현
		Sellers sellers = sellersService
			.findByName(principal.getName())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
		return ResponseSellerDto.builder()
			.id(sellers.getId())
			.email(sellers.getEmail())
			.companyName(sellers.getCompanyName())
			.email(sellers.getEmail()).build();
	}

	public String signup(@RequestBody @Valid SellerRegisterDto sellerRegisterDto) { //jwt 구현후 수정할것 DTO도 임시구현
		sellersService.signUp(sellerRegisterDto);
		return "회원가입에 성공했습니다.";
	}

	//뭐로 넘어오는지모르니 임시
	record MemberLoginReqBody(
		@NotBlank
		String username,

		@NotBlank
		String password
	) {
	}

	public String modify(Principal principal, @RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
		Sellers sellers = sellersService.findByName(principal.getName()).
			orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
		;
		sellersService.modify(sellers, customerUpdateDto);
		return "정보수정이 성공했습니다.";
	}

	public String login(@RequestBody @Valid MemberLoginReqBody reqBody) {
		Sellers sellers = sellersService.findByName(reqBody.username).
			orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
		if (!sellers.matchPassword(reqBody.password))
			throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");
		/*
		토큰생성
		 */
		return "토큰 반환";
	}
}
