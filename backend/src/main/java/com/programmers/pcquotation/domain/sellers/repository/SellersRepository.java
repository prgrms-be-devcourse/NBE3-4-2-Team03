package com.programmers.pcquotation.domain.sellers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;

@Repository
public interface SellersRepository extends JpaRepository<Sellers, Long> {
	Optional<Sellers> findByUsername(String username);

}
