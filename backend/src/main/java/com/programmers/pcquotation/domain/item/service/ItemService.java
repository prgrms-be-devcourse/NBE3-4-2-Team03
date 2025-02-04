package com.programmers.pcquotation.domain.item.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.category.entity.Category;
import com.programmers.pcquotation.domain.category.repository.CategoryRepository;
import com.programmers.pcquotation.domain.item.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	private final ImageService imageService;
	private final CategoryRepository categoryRepository;

	@Transactional
	public ItemCreateResponse addItem(final ItemCreateRequest request) {

		Category category = categoryRepository.findById(request.categoryId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID입니다."));

		String filename = imageService.storeImage(request.image());

		Item item = Item.builder()
			.category(category)
			.name(request.name())
			.imgFilename(filename)
			.build();

		Item savedItem = itemRepository.save(item);

		return new ItemCreateResponse(savedItem.getId(), "부품 생성 완료");
	}
}