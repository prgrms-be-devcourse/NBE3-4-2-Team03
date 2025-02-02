package com.programmers.pcquotation.domain.item.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.items.NewItem;
import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.implement.ItemManager;
import com.programmers.pcquotation.domain.items.service.ItemService;

class ItemServiceTest {
	private ItemService itemService;
	@Mock
	private ItemManager itemManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		itemService = new ItemService(itemManager);
	}

	@Test
	@DisplayName("addItem 데이터 추가, 저장")
	void addItemTest() {
		// Given
		ItemCreateRequest request = new ItemCreateRequest(
			"부품",
			"image.png"
		);

		// Mocking ItemManager
		when(itemManager.addItem(any(NewItem.class))).thenReturn(1L);

		// When
		ItemCreateResponse response = itemService.addItem(request);

		// Then
		// id와 message 값을 직접 비교
		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("부품생성 완료");
	}
}
