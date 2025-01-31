package com.programmers.pcquotation.sellers.DTO;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDto {
	String userName;
	@NotNull
	String password;
	String companyName;
	String email;
	String newPassword;
	String confirmNewPassword;

	@AssertTrue(message = "패스워드를 제외한 필드를 하나 이상 입력해야 합니다.")
	public boolean isValid() {
		return companyName != null || email != null || newPassword != null;
	}
}
