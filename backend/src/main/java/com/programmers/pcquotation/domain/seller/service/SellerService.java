package com.programmers.pcquotation.domain.seller.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.seller.dto.SellerRegisterDto;
import com.programmers.pcquotation.domain.seller.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
	@Autowired
	private final SellerRepository sellerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public Optional<Seller> findByName(String name) {
		return sellerRepository.findByUsername(name);
	}

	public Seller create(SellerRegisterDto sellerRegisterDto) {
		if (!sellerRegisterDto
			.getPassword()
			.equals(sellerRegisterDto
				.getConfirmPassword()))
			throw new NoSuchElementException("비밀번호가 일치하지않습니다.");
		Seller seller = Seller.builder()
			.username(sellerRegisterDto.getUsername())
			.password(passwordEncoder.encode(sellerRegisterDto.getPassword()))
			.companyName(sellerRegisterDto.getCompanyName())
			.email((sellerRegisterDto.getEmail()))
			.verificationQuestion(sellerRegisterDto.getVerificationQuestion())
			.verificationAnswer(sellerRegisterDto.getVerificationAnswer()).build();
		this.sellerRepository.save(seller);
		return seller;
	}

	public Seller modify(Seller seller, SellerUpdateDto customerUpdateDto) {
		if (seller.getPassword().equals(customerUpdateDto.getPassword())) {
			if (!customerUpdateDto.getUserName().isEmpty())
				seller.setUsername(customerUpdateDto.getUserName());
			if (!customerUpdateDto.getCompanyName().isEmpty())
				seller.setCompanyName(customerUpdateDto.getCompanyName());
			if (!customerUpdateDto.getNewPassword().isEmpty()) {
				if (customerUpdateDto.getNewPassword().equals(customerUpdateDto.getConfirmNewPassword()))
					seller.setPassword(customerUpdateDto.getNewPassword());
				else
					throw new NoSuchElementException("비밀번호가 일치하지않습니다.");
			}
		}
		this.sellerRepository.save(seller);
		return seller;
	}

}
