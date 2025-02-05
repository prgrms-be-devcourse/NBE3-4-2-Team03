package com.programmers.pcquotation.domain.sellers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.seller.entitiy.Seller;

@Repository
public interface SellersRepository extends JpaRepository<Seller, Long> {
	Optional<Seller> findByUsername(String username);
	Optional<Seller> findByApiKey(String apiKey);
}
