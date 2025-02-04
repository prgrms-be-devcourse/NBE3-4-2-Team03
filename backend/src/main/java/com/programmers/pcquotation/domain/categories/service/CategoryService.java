package com.programmers.pcquotation.domain.categories.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.categories.dto.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.dto.CategoryInfoResponse;
import com.programmers.pcquotation.domain.categories.dto.CategoryUpdateRequest;
import com.programmers.pcquotation.domain.categories.dto.CategoryUpdateResponse;
import com.programmers.pcquotation.domain.categories.entity.Categories;
import com.programmers.pcquotation.domain.categories.exception.CategoryNotFoundException;
import com.programmers.pcquotation.domain.categories.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	// 카테고리 생성
	@Transactional
	public CategoryCreateResponse addCategory(final CategoryCreateRequest request) {
		Categories categories = Categories.builder()
			.category(request.category())
			.build();

		Categories savedCategory = categoryRepository.save(categories);

		return new CategoryCreateResponse(savedCategory.getId(), "카테고리 생성 완료");
	}

	// 카테고리 전체 조회
	public List<CategoryInfoResponse> getList() {
		return categoryRepository.findAll().stream()
			.map(categories -> CategoryInfoResponse.builder()
				.id(categories.getId())
				.category(categories.getCategory())
				.build())
			.collect(Collectors.toList());
	}

	// 카테고리 수정
	@Transactional
	public CategoryUpdateResponse updateCategory(Long id, CategoryUpdateRequest request) {
		Categories categories = categoryRepository.findById(id)
			.orElseThrow(() -> new CategoryNotFoundException(id));

		categories.updateCategory(request.category());

		categoryRepository.save(categories);

		return new CategoryUpdateResponse(id, "카테고리 수정 완료");
	}
}