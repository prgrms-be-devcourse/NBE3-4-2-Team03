package com.programmers.pcquotation.domain.items;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Item {
	private final Long id;
	private final String name;
	private final String imgFilename;
}
