package com.programmers.pcquotation.domain.item.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.item.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public ItemCreateResponse addItem(final ItemCreateRequest request) { // 부품 생성
		Item items = Item.builder()
			.name(request.name())
			.imgFilename(request.imgFilename())
			.build();

		Item savedItem = itemRepository.save(items);

		return new ItemCreateResponse(savedItem.getId(), "부품 생성 완료");
	}
}
