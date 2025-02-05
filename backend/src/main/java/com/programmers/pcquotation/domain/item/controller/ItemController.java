package com.programmers.pcquotation.domain.item.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.item.dto.request.ItemCreateRequest;
import com.programmers.pcquotation.domain.item.dto.request.ItemUpdateRequest;
import com.programmers.pcquotation.domain.item.dto.response.ItemCreateResponse;
import com.programmers.pcquotation.domain.item.dto.response.ItemInfoResponse;
import com.programmers.pcquotation.domain.item.dto.response.ItemUpdateResponse;
import com.programmers.pcquotation.domain.item.service.ItemService;

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

	@GetMapping
	public List<ItemInfoResponse> getInfoList() {
		return itemService.getItemList();
	}

	@PutMapping("/{id}")
	public ItemUpdateResponse updateItem(
		@PathVariable Long id,
		@ModelAttribute("request") ItemUpdateRequest request
	) {
		return itemService.updateItem(id, request);
	}
}
