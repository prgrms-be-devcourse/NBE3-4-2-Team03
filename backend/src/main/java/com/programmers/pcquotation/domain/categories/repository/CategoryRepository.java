package com.programmers.pcquotation.domain.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.categories.entity.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
