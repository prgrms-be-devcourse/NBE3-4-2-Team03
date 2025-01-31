package com.programmers.pcquotation.domain.items.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.api.Result;
import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	//부품 추가
	@PostMapping("/api/items")
	public Result<ItemCreateResponse> createItem(
		@ModelAttribute("request") ItemCreateRequest request
	) {
		ItemCreateResponse response = itemService.addItem(request);
		return Result.success(response);
	}
}
