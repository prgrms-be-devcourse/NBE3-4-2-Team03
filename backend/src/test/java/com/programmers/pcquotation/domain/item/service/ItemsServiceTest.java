package com.programmers.pcquotation.domain.item.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.pcquotation.domain.categories.entity.Categories;
import com.programmers.pcquotation.domain.categories.repository.CategoryRepository;
import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.entity.Items;
import com.programmers.pcquotation.domain.items.repository.ItemRepository;
import com.programmers.pcquotation.domain.items.service.ImageService;
import com.programmers.pcquotation.domain.items.service.ItemService;

class ItemsServiceTest {
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ImageService imageService;

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	private MultipartFile mockFile; // 이미지 파일 Mock

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		itemService = new ItemService(itemRepository, imageService, categoryRepository);
	}

	@Test
	@DisplayName("addItem 데이터 추가, 저장 테스트")
	void addItemTest() {
		// Given
		String expectedImagePath = "uploads/img.png";
		when(imageService.storeImage(any(MultipartFile.class))).thenReturn(expectedImagePath);

		// 카테고리 Mock 객체 생성
		Categories mockCategory = mock(Categories.class); // mock 객체 생성
		when(mockCategory.getId()).thenReturn(2L); // mock 카테고리 ID 설정
		when(categoryRepository.findById(2L)).thenReturn(Optional.of(mockCategory)); // findById가 mockCategory를 반환하도록 설정

		ItemCreateRequest request = new ItemCreateRequest(2L, "부품", mockFile);
		Items items = Items.builder()
			.category(mockCategory)
			.name("부품")
			.imgFilename(expectedImagePath)
			.build();
		Items savedItem = Items.builder()
			.id(2L)
			.category(mockCategory)
			.name("부품")
			.imgFilename(expectedImagePath)
			.build();

		when(itemRepository.save(any(Items.class))).thenReturn(savedItem);

		// When
		ItemCreateResponse response = itemService.addItem(request);

		// Then
		assertThat(response.id()).isEqualTo(2L);
		assertThat(response.message()).isEqualTo("부품 생성 완료");
	}
}