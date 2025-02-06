package com.programmers.pcquotation.domain.estimaterequest.entity;

import java.time.LocalDateTime;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 20)
	private String purpose;

	@Column(columnDefinition = "INTEGER")
	private Integer budget;

	@Column(length = 200)
	private String otherRequest;

	LocalDateTime createDate;

	@ManyToOne
	private Customer customer;
}
