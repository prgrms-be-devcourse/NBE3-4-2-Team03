package com.programmers.pcquotation.domain.item.dto.response;

import com.programmers.pcquotation.domain.item.entity.Item;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ItemInfoResponse(
	@NonNull
	Long id,

	@NonNull
	String category,

	@NonNull
	String name,

	@NonNull
	String filename
) {
	public static ItemInfoResponse from(Item item) {
		return ItemInfoResponse.builder()
			.id(item.getId())
			.category(item.getCategory().getCategory())
			.name(item.getName())
			.filename(item.getImgFilename())
			.build();
	}
}
