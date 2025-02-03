package com.programmers.pcquotation.domain.categories.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.categories.NewCategory;
import com.programmers.pcquotation.domain.categories.dto.request.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryInfoResponse;
import com.programmers.pcquotation.domain.categories.implement.CategoryManager;
import com.programmers.pcquotation.domain.categories.implement.CategoryReader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryManager categoryManager;
	private final CategoryReader categoryReader;

	@Transactional
	public CategoryCreateResponse addCategory(final CategoryCreateRequest request) { //카테고리 생성
		NewCategory newCategory = new NewCategory(
			request.category()
		);

		Long savedCategoryId = categoryManager.addCategory(newCategory);

		return new CategoryCreateResponse(savedCategoryId, "카테고리 생성 완료");

	}

	public List<CategoryInfoResponse> getList() { //카테고리 조회
		return categoryReader.findAllCategories().stream()
			.map(category -> CategoryInfoResponse.builder()
				.id(category.getId())
				.category(category.getCategory())
				.build())
			.collect(Collectors.toList());

	}
}
