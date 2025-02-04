package com.programmers.pcquotation.domain.item.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ItemCreateRequest(
	@NotEmpty
	String name,
	@NotEmpty
	String imgFilename
) {
}
