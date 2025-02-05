package com.programmers.pcquotation.domain.item.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.pcquotation.domain.category.entity.Category;
import com.programmers.pcquotation.domain.category.repository.CategoryRepository;
import com.programmers.pcquotation.domain.item.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.repository.ItemRepository;

class ItemServiceTest {
	@InjectMocks // ItemService에 Mock 객체 자동 주입
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ImageService imageService;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private MultipartFile mockFile;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("addItem 데이터 추가, 저장 테스트")
	void addItemTest() {
		// Given
		String expectedImagePath = "uploads/img.png";
		when(imageService.storeImage(any(MultipartFile.class))).thenReturn(expectedImagePath);

		// 카테고리 Mock 객체 생성
		Category mockCategory = mock(Category.class);
		when(mockCategory.getId()).thenReturn(2L);
		when(categoryRepository.findById(2L)).thenReturn(Optional.of(mockCategory));

		ItemCreateRequest request = new ItemCreateRequest(2L, "부품", mockFile);
		Item item = Item.builder()
			.category(mockCategory)
			.name("부품")
			.imgFilename(expectedImagePath)
			.build();
		Item savedItem = Item.builder()
			.id(2L)
			.category(mockCategory)
			.name("부품")
			.imgFilename(expectedImagePath)
			.build();

		when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

		// When
		ItemCreateResponse response = itemService.addItem(request);

		// Then
		assertThat(response.id()).isEqualTo(2L);
		assertThat(response.message()).isEqualTo("부품 생성 완료");
	}

	@Test
	@DisplayName("부품 조회 테스트")
	void getItemListTest() {
		// 테스트용 카테고리 생성
		Category testCategory = Category.createTestCategory(1L, "GPU");

		// 테스트용 부품 생성
		Item item = Item.builder()
			.name("RTX 4090")
			.imgFilename("gpu.jpg")
			.category(testCategory)
			.build();

		// 검증
		assertNotNull(item);
		assertEquals("4090", item.getName());
		assertEquals("gpu", item.getImgFilename());
		assertEquals("GPU", item.getCategory().getCategory());
	}
}