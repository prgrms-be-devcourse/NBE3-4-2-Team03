package com.programmers.pcquotation.domain.items.implement;

import org.springframework.stereotype.Component;

import com.programmers.pcquotation.domain.items.NewItem;
import com.programmers.pcquotation.entity.item.ItemDetail;
import com.programmers.pcquotation.entity.item.ItemDetailRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component

public class ItemManager {

	private final ItemDetailRepository itemDetailRepository;

	public long addItem(NewItem item) {

		ItemDetail itemDetail = ItemDetail.builder()
			.name(item.getName())
			.imgFilename(item.getImgFilename())
			.build();

		ItemDetail savedItem = itemDetailRepository.save(itemDetail);

		return savedItem.getId();

	}
}
