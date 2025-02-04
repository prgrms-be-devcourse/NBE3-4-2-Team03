package com.programmers.pcquotation.domain.categories.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryUpdateRequest(

	@NotEmpty
	String category
) {
}
