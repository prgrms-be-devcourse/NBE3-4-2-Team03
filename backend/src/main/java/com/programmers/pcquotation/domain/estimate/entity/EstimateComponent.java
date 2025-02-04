package com.programmers.pcquotation.domain.estimate.entity;

import com.programmers.pcquotation.domain.category.entity.Category;
import com.programmers.pcquotation.domain.item.entity.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimateComponent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "estimate_id")
	private Estimate estimates;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	private Integer price;

	@Builder
	public EstimateComponent(Estimate estimates, Category category, Item item, Integer price) {
		this.estimates = estimates;
		this.category = category;
		this.item = item;
		this.price = price;
	}
}