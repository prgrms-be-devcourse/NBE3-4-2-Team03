package com.programmers.pcquotation.domain.seller.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.seller.dto.SellerRegisterDto;
import com.programmers.pcquotation.domain.seller.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.repository.SellerRepository;
import com.programmers.pcquotation.domain.seller.service.AuthTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
	private final SellerRepository sellerRepository;
	private final AuthTokenService authTokenService;
	private final PasswordEncoder passwordEncoder;


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
			.apiKey(UUID.randomUUID().toString())
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
	public void setIsVerified(Seller seller,boolean isVerified){
		seller.setVerified(isVerified);
		sellerRepository.save(seller);
	}
	public Optional<Seller> findById(Long id) {
		return sellerRepository.findById(id);
	}

	public Optional<Seller> findByApiKey(String apiKey) {
		return sellerRepository.findByApiKey(apiKey);
	}
	public boolean matchPassword(Seller sellers,String password){
		return passwordEncoder.matches(password,sellers.getPassword());
	}
	public String getAccessToken(Seller sellers) {
		return authTokenService.getAccessToken(sellers);
	}

	public String getAuthToken(Seller sellers) {
		return sellers.getApiKey() + " " + getAccessToken(sellers);
	}

	public Seller getMemberFromAccessToken(String accessToken) {
		Map<String, Object> payload = authTokenService.payload(accessToken);

		if(payload == null) return null;

		long id = (long) payload.get("id");

		return findById(id).get();
	}
}
