package com.programmers.pcquotation.domain.item.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemUpdateRequest(
	@NotBlank
	String name,

	@NotBlank
	String imgFilename,

	@NotNull
	Long categoryId
) {
}
