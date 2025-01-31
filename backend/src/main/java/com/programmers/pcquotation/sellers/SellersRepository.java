package com.programmers.pcquotation.sellers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellersRepository extends JpaRepository<Sellers, Long> {
	Optional<Sellers> findByUsername(String username);

}
