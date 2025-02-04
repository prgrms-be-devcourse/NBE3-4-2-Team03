package com.programmers.pcquotation.domain.estimate.dto;

import lombok.Getter;

@Getter
public class EstimateItemDto {
	private String category; // "CPU", "메인보드"
	private Long item;      // "Intel i5-13500k"
	private Integer price;    // 135000
}