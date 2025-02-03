package com.programmers.pcquotation.domain.categories.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.api.Result;
import com.programmers.pcquotation.domain.categories.dto.request.CategoryCreateRequest;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryCreateResponse;
import com.programmers.pcquotation.domain.categories.dto.response.CategoryInfoResponse;
import com.programmers.pcquotation.domain.categories.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryContorller {

	private final CategoryService categoryService;

	// 카테고리 추가
	@PostMapping("/api/categories")
	public Result<CategoryCreateResponse> createProduct(
		@RequestBody CategoryCreateRequest request
	) {
		CategoryCreateResponse response = categoryService.addCategory(request);
		return Result.success(response);
	}

	//카테고리 다건조회
	@GetMapping("/api/categories")
	public Result<List<CategoryInfoResponse>> list() {
		List<CategoryInfoResponse> response = categoryService.getList();
		return Result.success(response);
	}
}