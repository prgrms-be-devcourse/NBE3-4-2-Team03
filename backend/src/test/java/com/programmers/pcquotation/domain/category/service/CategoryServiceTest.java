package com.programmers.pcquotation.domain.category.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.category.dto.CategoryCreateRequest;
import com.programmers.pcquotation.domain.category.dto.CategoryCreateResponse;
import com.programmers.pcquotation.domain.category.dto.CategoryDeleteResponse;
import com.programmers.pcquotation.domain.category.dto.CategoryInfoResponse;
import com.programmers.pcquotation.domain.category.dto.CategoryUpdateRequest;
import com.programmers.pcquotation.domain.category.entity.Category;
import com.programmers.pcquotation.domain.category.repository.CategoryRepository;

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
		Category category = Category.builder().category("카테고리").build();
		Category savedCategory = Category.builder().id(1L).category("카테고리").build();

		when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

		CategoryCreateResponse response = categoryService.addCategory(request);

		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("카테고리 생성 완료");
	}

	@Test
	@DisplayName("카테고리 다건조회 테스트")
	void getCategoryListTest() {

		Category category1 = Category.builder().id(1L).category("카테고리").build();
		Category category2 = Category.builder().id(2L).category("카테고리2").build();

		when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

		List<CategoryInfoResponse> response = categoryService.getCategoryList();

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
		Category existingCategory = Category.createTestCategory(categoryId, "기존 카테고리");
		CategoryUpdateRequest updateRequest = new CategoryUpdateRequest("수정된 카테고리");

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

		categoryService.updateCategory(categoryId, updateRequest);

		assertThat(existingCategory.getCategory()).isEqualTo("수정된 카테고리");
	}

	@Test
	@DisplayName("카테고리 삭제 테스트")
	void deleteCategoryTest() {

		Long categoryId = 1L;
		Category existingCategory = Category.createTestCategory(1L, "기존 카테고리");
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

		CategoryDeleteResponse response = categoryService.deleteCategory(categoryId);

		assertThat(response.id()).isEqualTo(categoryId);
		assertThat(response.message()).isEqualTo("카테고리 삭제 완료");
		verify(categoryRepository).delete(any(Category.class));

	}

}