package com.programmers.pcquotation.domain.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programmers.pcquotation.domain.customers.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}