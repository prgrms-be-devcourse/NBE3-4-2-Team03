package com.programmers.pcquotation.domain.items.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.items.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.items.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.items.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")

public class ItemController {

	private final ItemService itemService;

	@PostMapping
	public ItemCreateResponse createItem(
		@ModelAttribute("request") ItemCreateRequest request
	) {
		return itemService.addItem(request);
	}
}