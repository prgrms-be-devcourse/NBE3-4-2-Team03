package com.programmers.pcquotation.domain.estimate.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.pcquotation.domain.estimate.dto.EstimateCreateRequest;
import com.programmers.pcquotation.domain.estimate.dto.EstimateItemDto;
import com.programmers.pcquotation.domain.estimate.repository.EstimateRepository;
import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.estimaterequest.service.EstimateRequestService;
import com.programmers.pcquotation.domain.item.entity.Item;
import com.programmers.pcquotation.domain.item.repository.ItemRepository;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.SellerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstimateService {
	private final EstimateRepository estimateRepository;
	private final EstimateRequestService estimateRequestService;
	private final SellerService sellerService;
	private final ItemRepository itemRepository;

	@Transactional
	public void createEstimate(EstimateCreateRequest request) {
		// 견적 요청과 판매자 정보 조회
		EstimateRequest estimateRequest = estimateRequestService.getEstimateRequestById(request.getEstimateRequestId())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 견적 요청입니다."));

		Seller seller = sellerService.findByUserName(request.getSellerId())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 판매자입니다."));
		List<Item> itemsList = itemRepository.findAllById(
			request.getItem().stream().map(EstimateItemDto::getItem).toList());

		// List<EstimateComponent> tempComp = itemsList.stream().map(item->
		// )
		//
		//
		//
		// // Estimates 엔티티 생성 및 저장
		// Estimate estimate = Estimate.builder()
		// 	.estimateRequest(estimateRequest)
		// 	.sellers(seller)
		// 	.totalPrice(getTotalEstimates(request.getItems()))
		// 	.estimateComponents(components)
		// 	.build();
		//
		// // 양방향 연관관계 설정
		// components.forEach(component -> component.setEstimates(estimate));

		// estimateRepository.save(estimate);
	}

	public Integer getTotalEstimates(List<EstimateItemDto> items) {
		Integer total = 0;
		for (EstimateItemDto item : items) {
			total += item.getPrice();
		}
		return total;
	}
}
