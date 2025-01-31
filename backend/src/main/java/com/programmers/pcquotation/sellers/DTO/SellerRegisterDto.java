package com.programmers.pcquotation.sellers.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegisterDto {
	@NotNull
	String username;
	@NotNull
	String password;
	@NotNull
	String confirmPassword;
	@NotNull
	String companyName;
	@NotNull
	String email;
	@NotNull
	String verificationQuestion;
	@NotNull
	String verificationAnswer;
}
