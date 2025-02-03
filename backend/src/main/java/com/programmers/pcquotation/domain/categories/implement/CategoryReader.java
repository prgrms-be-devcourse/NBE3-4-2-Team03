package com.programmers.pcquotation.domain.categories.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.pcquotation.entity.category.CategoryDetail;
import com.programmers.pcquotation.entity.category.CategoryDetailRepository;

@Component
public class CategoryReader {

	private final CategoryDetailRepository categoryRepository;

	public CategoryReader(CategoryDetailRepository categoryRepository) {
		this.categoryRepository = categoryRepository;

	}

	public List<CategoryDetail> findAllCategories() {
		return categoryRepository.findAll();
	}
}

