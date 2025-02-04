package com.programmers.pcquotation.domain.items.dto.response;

import org.springframework.lang.NonNull;

public record ItemCreateResponse(
	@NonNull
	Long id,

	@NonNull
	String message // 예: "부품이 성공적으로 생성되었습니다."
) {
}
