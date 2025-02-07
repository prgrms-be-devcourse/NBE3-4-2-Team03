package com.programmers.pcquotation.domain.item.dto;

import com.programmers.pcquotation.domain.item.entity.Item;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ItemInfoResponse(
	@NonNull
	Long id,

	@NotBlank
	String name,

	@NonNull
	Long categoryId,

	@NotBlank
	String categoryName,

	@NotBlank
	String filename
) {
	public static ItemInfoResponse from(Item item) {
		return ItemInfoResponse.builder()
			.id(item.getId())
			.name(item.getName())
			.categoryId(item.getCategory().getId())
			.categoryName(item.getCategory().getCategory())
			.filename(item.getImgFilename())
			.build();
	}
}
