package com.programmers.pcquotation.domain.categories.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.pcquotation.domain.categories.NewCategory;
import com.programmers.pcquotation.domain.categories.dto.request.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryInfoResponse;
import com.programmers.pcquotation.domain.categories.implement.CategoryManager;
import com.programmers.pcquotation.domain.categories.implement.CategoryReader;
import com.programmers.pcquotation.entity.category.CategoryDetail;

public class CategoryServiceTest {
	private CategoryService categoryService;
	@Mock
	private CategoryManager categoryManager;
	@Mock
	private CategoryReader categoryReader;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		categoryService = new CategoryService(categoryManager, categoryReader);
	}

	@Test
	@DisplayName("addCategory 데이터 추가, 저장 테스트")
	void addCategoryTest() {
		CategoryCreateRequest request = new CategoryCreateRequest(
			"카테고리"
		);
		when(categoryManager.addCategory(any(NewCategory.class))).thenReturn(1L);

		CategoryCreateResponse response = categoryService.addCategory(request);

		assertThat(response.id()).isEqualTo(1L);
		assertThat(response.message()).isEqualTo("카테고리 생성 완료");
	}

	@Test
	@DisplayName("카테고리 다건조회 테스트")
	void getListTest() {

		CategoryDetail category1 = mock(CategoryDetail.class);
		CategoryDetail category2 = mock(CategoryDetail.class);

		when(category1.getId()).thenReturn(1L);
		when(category1.getCategory()).thenReturn("카테고리");
		when(category2.getId()).thenReturn(2L);
		when(category2.getCategory()).thenReturn("카테고리2");

		when(categoryReader.findAllCategories()).thenReturn(List.of(category1, category2));

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
}
