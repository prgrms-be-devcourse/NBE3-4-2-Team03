package com.programmers.pcquotation.sellers;

import static jakarta.persistence.GenerationType.*;

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
public class Sellers {
	@Id
	@GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
	private Long id;
	@Column(length = 20)
	private String username;
	@Column(length = 20)
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

	/*
	// 추천한 유저 목록
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "seller_recommend",
		joinColumns = @JoinColumn(name = "seller_id"),
		inverseJoinColumns = @JoinColumn(name = "customer_id")
	)
	private Set<Customers> recommend = new HashSet<>();
	*/

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

}
