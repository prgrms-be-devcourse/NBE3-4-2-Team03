package com.programmers.pcquotation.domain.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.items.entity.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {
}
