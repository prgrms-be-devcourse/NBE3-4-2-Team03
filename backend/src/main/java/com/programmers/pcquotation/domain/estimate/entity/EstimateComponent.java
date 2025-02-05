package com.programmers.pcquotation.domain.estimate.entity;

import com.programmers.pcquotation.domain.item.entity.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
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
	private Estimate estimate;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	private Integer price;

	// 생성자를 통한 초기화
	public EstimateComponent(Item item, Integer price) {
		this.item = item;
		this.price = price;
	}

	public void setEstimate(Estimate estimate) {
		this.estimate = estimate;
	}

	public static EstimateComponent createComponent(Item item, Integer price) {
		return new EstimateComponent(item, price);
	}
}