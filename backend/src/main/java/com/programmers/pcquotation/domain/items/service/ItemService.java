package com.programmers.pcquotation.domain.items.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.entity.Items;
import com.programmers.pcquotation.domain.items.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public ItemCreateResponse addItem(final ItemCreateRequest request) { // 부품 생성
		Items items = Items.builder()
			.name(request.name())
			.imgFilename(request.imgFilename())
			.build();

		Items savedItem = itemRepository.save(items);

		return new ItemCreateResponse(savedItem.getId(), "부품 생성 완료");
	}
}
