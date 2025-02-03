package com.programmers.pcquotation.sellers.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//임시구현
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSellerDto {
	private Long id;
	private String username;
	private String companyName;
	private String email;

}
