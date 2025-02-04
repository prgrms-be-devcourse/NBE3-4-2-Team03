package com.programmers.pcquotation.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
