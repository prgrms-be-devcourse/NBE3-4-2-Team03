package com.programmers.pcquotation.domain.item.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ItemUpdateRequest(
	@NotBlank
	String name,
	@NotBlank
	String imgFilename,
	@NotBlank
	Long categoryId
) {
}
