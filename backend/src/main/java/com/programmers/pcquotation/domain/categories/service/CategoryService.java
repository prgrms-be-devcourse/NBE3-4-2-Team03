package com.programmers.pcquotation.domain.categories.service;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.categories.NewCategory;
import com.programmers.pcquotation.domain.categories.dto.request.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.implement.CategoryManager;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryManager categoryManager;

	@Transactional
	public CategoryCreateResponse addCategory(final CategoryCreateRequest request) {
		NewCategory newCategory = new NewCategory(
			request.category()
		);

		Long savedCategoryId = categoryManager.addCategory(newCategory);

		return new CategoryCreateResponse(savedCategoryId, "카테고리 생성 완료");

	}
}
