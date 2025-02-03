package com.programmers.pcquotation.domain.categories.implement;

import org.springframework.stereotype.Component;

import com.programmers.pcquotation.domain.categories.NewCategory;
import com.programmers.pcquotation.entity.category.CategoryDetail;
import com.programmers.pcquotation.entity.category.CategoryDetailRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CategoryManager {

	private final CategoryDetailRepository categoryDetailRepository;

	public long addCategory(NewCategory category) {

		CategoryDetail categoryDetail = CategoryDetail.builder()
			.category(category.getCategory())
			.build();

		CategoryDetail savedCategory = categoryDetailRepository.save(categoryDetail);

		return savedCategory.getId();
	}
}
