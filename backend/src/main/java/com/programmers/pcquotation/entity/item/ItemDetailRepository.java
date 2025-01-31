package com.programmers.pcquotation.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
