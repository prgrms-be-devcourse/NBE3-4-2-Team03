package com.programmers.pcquotation.domain.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String category;

	// @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<Item> item = new ArrayList<>();

	public void updateCategory(String category) {
		this.category = category;
	}

	public static Category createTestCategory(Long id, String category) {
		Category categories = new Category();
		categories.id = id;
		categories.category = category;
		return categories;
	}
}