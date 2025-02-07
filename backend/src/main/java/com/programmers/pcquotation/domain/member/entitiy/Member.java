package com.programmers.pcquotation.domain.member.entitiy;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;



public interface Member<T> {
	long getId();
	String getUsername();
	Collection<? extends GrantedAuthority> getAuthorities();
	String getApiKey();
	String getPassword();
}
