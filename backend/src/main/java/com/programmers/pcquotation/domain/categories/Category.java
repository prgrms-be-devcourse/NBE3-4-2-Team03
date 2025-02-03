package com.programmers.pcquotation.domain.categories;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Category {
	private final Long id;
	private final String category;
}
