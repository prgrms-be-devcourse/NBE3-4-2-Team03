package com.programmers.pcquotation.domain.sellers.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.sellers.dto.SellerRegisterDto;
import com.programmers.pcquotation.domain.sellers.dto.SellerUpdateDto;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import com.programmers.pcquotation.domain.sellers.repository.SellersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellersService {
	@Autowired
	private final SellersRepository sellersRepository;
	@Autowired
	private final AuthTokenService authTokenService;
	@Autowired
	PasswordEncoder passwordEncoder;

	public Optional<Sellers> findByName(String name) {
		return sellersRepository.findByUsername(name);
	}

	public Sellers create(SellerRegisterDto sellerRegisterDto) {
		if (!sellerRegisterDto
			.getPassword()
			.equals(sellerRegisterDto
				.getConfirmPassword()))
			throw new NoSuchElementException("비밀번호가 일치하지않습니다.");
		Sellers sellers = Sellers.builder()
			.username(sellerRegisterDto.getUsername())
			.password(passwordEncoder.encode(sellerRegisterDto.getPassword()))
			.companyName(sellerRegisterDto.getCompanyName())
			.email((sellerRegisterDto.getEmail()))
			.verificationQuestion(sellerRegisterDto.getVerificationQuestion())
			.verificationAnswer(sellerRegisterDto.getVerificationAnswer()).build();
		this.sellersRepository.save(sellers);
		return sellers;
	}

	public Sellers modify(Sellers sellers, SellerUpdateDto customerUpdateDto) {
		if (sellers.getPassword().equals(customerUpdateDto.getPassword())) {
			if (!customerUpdateDto.getUserName().isEmpty())
				sellers.setUsername(customerUpdateDto.getUserName());
			if (!customerUpdateDto.getCompanyName().isEmpty())
				sellers.setCompanyName(customerUpdateDto.getCompanyName());
			if (!customerUpdateDto.getNewPassword().isEmpty()) {
				if (customerUpdateDto.getNewPassword().equals(customerUpdateDto.getConfirmNewPassword()))
					sellers.setPassword(customerUpdateDto.getNewPassword());
				else
					throw new NoSuchElementException("비밀번호가 일치하지않습니다.");
			}
		}
		this.sellersRepository.save(sellers);
		return sellers;
	}

	public Optional<Sellers> findById(Long id) {
		return sellersRepository.findById(id);
	}

	//public Optional<Sellers> findByApiKey(String apiKey) {
	//	return sellersRepository.findByApiKey(apiKey);
	//}
	public boolean matchPassword(Sellers sellers,String password){
		return passwordEncoder.matches(password,sellers.getPassword());
	}
	public String getAccessToken(Sellers sellers) {
		return authTokenService.getAccessToken(sellers);
	}

	public String getAuthToken(Sellers sellers) {
		return sellers.getApiKey() + " " + getAccessToken(sellers);
	}

	public Sellers getMemberFromAccessToken(String accessToken) {
		Map<String, Object> payload = authTokenService.payload(accessToken);

		if(payload == null) return null;

		long id = (long) payload.get("id");

		return findById(id).get();
	}
}
