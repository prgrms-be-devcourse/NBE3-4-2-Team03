package com.programmers.pcquotation.domain.categories.entity;

import java.util.ArrayList;
import java.util.List;

import com.programmers.pcquotation.domain.items.entity.Items;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Items> items = new ArrayList<>();

	public void updateCategory(String category) {
		this.category = category;
	}

	public static Categories createTestCategory(Long id, String category) {
		Categories categories = new Categories();
		categories.id = id;
		categories.category = category;
		return categories;
	}
}