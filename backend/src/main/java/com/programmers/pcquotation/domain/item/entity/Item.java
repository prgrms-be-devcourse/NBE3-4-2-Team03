package com.programmers.pcquotation.domain.item.entity;

import com.programmers.pcquotation.domain.category.entity.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;        // 부품 이름

	private String imgFilename;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public void updateItem(String name, String imgFilename, Category category) {
		this.name = name;
		this.imgFilename = imgFilename;
		this.category = category;
	}

	public static Item createTestItem(
		Long id,
		String name,
		String imgFilename,
		Category category
	) {

		Item item = new Item();
		item.id = id;
		item.name = name;
		item.imgFilename = imgFilename;
		item.category = category;
		return item;
	}
}

