package com.programmers.pcquotation.domain.items.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.items.NewItem;
import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.implement.ItemManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemManager itemManager;

	public ItemCreateResponse addItem(final ItemCreateRequest request) {

		NewItem newItem = new NewItem(
			request.name(),
			request.imgFilename()
		);

		Long savedItemId = itemManager.addItem(newItem);

		return new ItemCreateResponse(savedItemId, "부품생성 완료");
	}
}
