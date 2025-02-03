package com.programmers.pcquotation.domain.categories.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.categories.NewCategory;
import com.programmers.pcquotation.domain.categories.dto.request.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.implement.CategoryManager;

public class CategoryServiceTest {
	private CategoryService categoryService;
	@Mock
	private CategoryManager categoryManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		categoryService = new CategoryService(categoryManager);
	}

	@Test
	@DisplayName("addCategory 데이터 추가, 저장")
	void addCategoryTest() {
		CategoryCreateRequest request = new CategoryCreateRequest(
			"카테고리"
		);
		when(categoryManager.addCategory(any(NewCategory.class))).thenReturn(1L);

		CategoryCreateResponse response = categoryService.addCategory(request);

		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("카테고리 생성 완료");
	}
}
