package com.programmers.pcquotation.domain.comment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(

	@NotNull
	Integer estimateId,

	@NotNull
	Long customerId,

	@NotNull
	Long sellerId,

	@NotEmpty
	String content,

	@NotEmpty
	LocalDateTime createDate

) {
}
