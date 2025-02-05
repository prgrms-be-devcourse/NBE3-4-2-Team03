package com.programmers.pcquotation.domain.estimate.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.pcquotation.domain.estimate.dto.EstimateCreateRequest;
import com.programmers.pcquotation.domain.estimate.dto.EstimateItemDto;
import com.programmers.pcquotation.domain.estimate.dto.ReceivedQuoteDTO;
import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import com.programmers.pcquotation.domain.estimate.entity.EstimateComponent;
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

		EstimateRequest estimateRequest = estimateRequestService.getEstimateRequestById(request.getEstimateRequestId())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 견적 요청입니다."));

		Seller seller = sellerService.findByUserName(request.getSellerId())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 판매자입니다."));

		Estimate estimate = Estimate.builder()
			.estimateRequest(estimateRequest)
			.seller(seller)
			.totalPrice(getTotalPrice(request.getItem()))
			.build();

		List<EstimateComponent> components = request.getItem().stream()
			.map(itemDto -> {
				Item item = itemRepository.findById(itemDto.getItem())
					.orElseThrow(() -> new NoSuchElementException("존재하지 않는 아이템입니다."));
				return EstimateComponent.createComponent(item, itemDto.getPrice(), estimate);
			})
			.toList();

		estimate.setEstimateComponents(components);

		estimateRepository.save(estimate);
	}

	public Integer getTotalPrice(List<EstimateItemDto> items) {
		Integer total = 0;
		for (EstimateItemDto item : items) {
			total += item.getPrice();
		}
		return total;
	}

	public List<ReceivedQuoteDTO> getEstimateByRequest(Integer id) {
		List<Estimate> list = estimateRepository.getAllByEstimateRequest_Id(id);

		return list.stream().map(quoto -> {
			return ReceivedQuoteDTO.builder()
				.id(quoto.getId())
				.seller(quoto.getSeller().getUsername())
				.date(quoto.getCreateDate())
				.totalPrice(quoto.getTotalPrice())
				.items(quoto.getEstimateComponents().stream()
					.collect(Collectors.toMap(
						item -> item.getItem().getCategory().getCategory(),
						item -> item.getItem().getName())))
				.build();
		}).toList();
	}
}
