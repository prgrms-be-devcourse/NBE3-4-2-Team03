package com.programmers.pcquotation.domain.comment.emtity;

import java.time.LocalDateTime;

import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.estimate.entity.Estimate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "estimate_id")
	private Estimate estimate;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Customer author;

	private String content;

	private LocalDateTime createDate;

	public void updateComment(
		Estimate estimate,
		Customer author,
		String content
	) {
		this.estimate = estimate;
		this.author = author;
		this.content = content;

	}
}