package com.programmers.pcquotation.domain.items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewItem {
	private final String name;
	private final String imgFilename;
}
