package com.programmers.pcquotation.domain.item.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.item.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.repository.ItemRepository;

class ItemsServiceTest {
	private ItemService itemService;
	@Mock
	private ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		itemService = new ItemService(itemRepository);
	}

	@Test
	@DisplayName("addItem 데이터 추가, 저장 테스트")
	void addItemTest() {
		// Given
		ItemCreateRequest request = new ItemCreateRequest("부품", "img.png");
		Item items = Item.builder()
			.name("부품")
			.imgFilename("img.png")
			.build();
		Item savedItem = Item.builder()
			.id(1L)
			.name("부품2")
			.imgFilename("img2.png").
			build();

		when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

		// When
		ItemCreateResponse response = itemService.addItem(request);

		// Then
		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("부품 생성 완료");
	}
}