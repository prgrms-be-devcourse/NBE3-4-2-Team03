package com.programmers.pcquotation.domain.items.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ItemCreateRequest(
	@NotEmpty
	String name,
	@NotEmpty
	String imgFilename
) {
}
