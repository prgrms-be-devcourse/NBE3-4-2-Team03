package com.programmers.pcquotation.domain.categories.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CategoryCreateRequest(
	@NotEmpty
	String category
) {
}
