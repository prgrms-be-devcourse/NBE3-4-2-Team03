package com.programmers.pcquotation.domain.categories.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.categories.dto.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.dto.CategoryInfoResponse;
import com.programmers.pcquotation.domain.categories.dto.CategoryUpdateRequest;
import com.programmers.pcquotation.domain.categories.entity.Categories;
import com.programmers.pcquotation.domain.categories.repository.CategoryRepository;

public class CategoryServiceTest {

	private CategoryService categoryService;

	@Mock
	private CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		categoryService = new CategoryService(categoryRepository);
	}

	@Test
	@DisplayName("addCategory 데이터 추가, 저장 테스트")
	void addCategoryTest() {

		CategoryCreateRequest request = new CategoryCreateRequest("카테고리");
		Categories categories = Categories.builder().category("카테고리").build();
		Categories savedCategory = Categories.builder().id(1L).category("카테고리").build();

		when(categoryRepository.save(any(Categories.class))).thenReturn(savedCategory);

		CategoryCreateResponse response = categoryService.addCategory(request);

		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("카테고리 생성 완료");
	}

	@Test
	@DisplayName("카테고리 다건조회 테스트")
	void getListTest() {

		Categories category1 = Categories.builder().id(1L).category("카테고리").build();
		Categories category2 = Categories.builder().id(2L).category("카테고리2").build();

		when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

		List<CategoryInfoResponse> response = categoryService.getList();

		List<CategoryInfoResponse> expectedList = List.of(
			new CategoryInfoResponse(1L, "카테고리"),
			new CategoryInfoResponse(2L, "카테고리2")
		);

		assertThat(response).hasSize(expectedList.size());
		for (int i = 0; i < response.size(); i++) {
			assertThat(response.get(i).id()).isEqualTo(expectedList.get(i).id());
			assertThat(response.get(i).category()).isEqualTo(expectedList.get(i).category());
		}
	}

	@Test
	@DisplayName("카테고리 수정 테스트")
	void updateCategoryTest() {

		Long categoryId = 1L;
		Categories existingCategory = Categories.createTestCategory(categoryId, "기존 카테고리");
		CategoryUpdateRequest updateRequest = new CategoryUpdateRequest("수정된 카테고리");

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

		categoryService.updateCategory(categoryId, updateRequest);

		assertThat(existingCategory.getCategory()).isEqualTo("수정된 카테고리");
	}
}