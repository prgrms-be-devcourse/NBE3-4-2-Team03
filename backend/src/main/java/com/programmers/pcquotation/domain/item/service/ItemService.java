package com.programmers.pcquotation.domain.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.category.entity.Category;
import com.programmers.pcquotation.domain.category.repository.CategoryRepository;
import com.programmers.pcquotation.domain.item.dto.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.dto.ItemDeleteResponse;
import com.programmers.pcquotation.domain.item.dto.ItemInfoResponse;
import com.programmers.pcquotation.domain.item.dto.ItemUpdateRequest;
import com.programmers.pcquotation.domain.item.dto.ItemUpdateResponse;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.exception.ItemNotFoundException;
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

	//부품 생성
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

	//부품 조회
	@Transactional
	public List<ItemInfoResponse> getItemList() {
		List<Item> items = itemRepository.findAll();
		return items.stream()
			.map(item -> new ItemInfoResponse(
				item.getId(),
				item.getName(),                     // 부품 이름 (name)
				item.getCategory().getId(),         // 카테고리 ID
				item.getCategory().getCategory(),   // 카테고리 이름 (categoryName)
				item.getImgFilename()               // 이미지 파일명
			))
			.collect(Collectors.toList());
	}

	//부품 수정
	@Transactional
	public ItemUpdateResponse updateItem(Long id, ItemUpdateRequest request) {

		Item item = itemRepository.findById(id)
			.orElseThrow(() -> new ItemNotFoundException(id));

		Category category = categoryRepository.findById(request.categoryId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID입니다."));

		item.updateItem(
			request.name(),
			request.imgFilename(),
			category
		);

		return new ItemUpdateResponse(id, "부품 수정 완료");

	}

	//부품 삭제
	@Transactional
	public ItemDeleteResponse deleteItem(Long id) {
		Item item = itemRepository.findById(id)
			.orElseThrow(() -> new ItemNotFoundException(id));

		itemRepository.delete(item);

		return new ItemDeleteResponse(id, "부품 삭제 완료");
	}
}