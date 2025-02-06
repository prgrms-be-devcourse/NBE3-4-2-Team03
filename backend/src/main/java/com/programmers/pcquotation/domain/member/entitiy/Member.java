package com.programmers.pcquotation.domain.member.entitiy;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;

public interface Member<T> {
	long getId();
	String getUsername();
	Collection<? extends GrantedAuthority> getAuthorities();
	String getApiKey();
}
