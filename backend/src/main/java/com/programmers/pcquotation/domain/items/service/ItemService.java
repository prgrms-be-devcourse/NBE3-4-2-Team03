package com.programmers.pcquotation.domain.items.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.categories.entity.Categories;
import com.programmers.pcquotation.domain.categories.repository.CategoryRepository;
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
	private final ImageService imageService;
	private final CategoryRepository categoryRepository;

	@Transactional
	public ItemCreateResponse addItem(final ItemCreateRequest request) {

		Categories categories = categoryRepository.findById(request.categoryId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID입니다."));

		String filename = imageService.storeImage(request.image());

		Items items = Items.builder()
			.category(categories)
			.name(request.name())
			.imgFilename(filename)
			.build();

		Items savedItem = itemRepository.save(items);
		return new ItemCreateResponse(savedItem.getId(), "부품 생성 완료");
	}
}