package com.programmers.pcquotation.domain.categories.dto.response;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CategoryInfoResponse(

	@NonNull
	Long id,

	@NonNull
	String category

) {
}
