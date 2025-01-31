package com.programmers.pcquotation.sellers;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.sellers.DTO.CustomerUpdateDto;
import com.programmers.pcquotation.sellers.DTO.SellerRegisterDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellersService {
	private final SellersRepository sellersRepository;

	Optional<Sellers> findByName(String name) {
		return sellersRepository.findByUsername(name);
	}

	//시큐리티 추가후 패스워드 암호화하기
	Sellers signUp(SellerRegisterDto sellerRegisterDto) {
		if (!sellerRegisterDto
			.getPassword()
			.equals(sellerRegisterDto
				.getConfirmPassword()))
			throw new NoSuchElementException("비밀번호가 일치하지않습니다.");
		Sellers sellers = Sellers.builder()
			.username(sellerRegisterDto.getUsername())
			.password(sellerRegisterDto.getPassword())
			.companyName(sellerRegisterDto.getCompanyName())
			.email((sellerRegisterDto.getEmail()))
			.verificationQuestion(sellerRegisterDto.getVerificationQuestion())
			.verificationAnswer(sellerRegisterDto.getVerificationAnswer()).build();
		this.sellersRepository.save(sellers);
		return sellers;
	}

	Sellers modify(Sellers sellers, CustomerUpdateDto customerUpdateDto) {
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

}
