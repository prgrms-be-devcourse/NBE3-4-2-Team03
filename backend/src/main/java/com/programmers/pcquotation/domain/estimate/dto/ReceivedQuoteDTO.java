package com.programmers.pcquotation.domain.estimate.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter                 // JSON 직렬화를 위해 필요
@Builder
@NoArgsConstructor     // 기본 생성자
@AllArgsConstructor
public class ReceivedQuoteDTO {
	private Integer id;                  // 받은 견적 ID
	private String seller;            // 판매자 이름
	private LocalDateTime date;              // 견적 받은 날짜
	private Integer totalPrice;        // 총 견적 금액
	private Map<String, String> items;// 부품 목록 (예: {"cpu": "Intel i5-13400F", ...})
}
