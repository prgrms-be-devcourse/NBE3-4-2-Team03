package com.programmers.pcquotation.domain.seller.entitiy;

import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
	@Id
	@GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
	private Long id;
	@Column(length = 20)
	private String username;
	@Column(length = 255)
	private String password;
	@Column(length = 20)
	private String companyName;
	@Column(length = 100)
	private String email;
	@Column(length = 100)
	private String verificationQuestion;
	@Column(length = 100)
	private String verificationAnswer;
	private boolean isVerified;
	@Column(unique = true)
	private String apiKey;
	/*
	// 추천한 유저 목록
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Customers> recommend = new HashSet<>();
	*/


	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthoritiesAsStringList()
			.stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	public List<String> getAuthoritiesAsStringList() {
		List<String> authorities = new ArrayList<>();

		authorities.add("ROLE_SELLER");

		return authorities;
	}

}
