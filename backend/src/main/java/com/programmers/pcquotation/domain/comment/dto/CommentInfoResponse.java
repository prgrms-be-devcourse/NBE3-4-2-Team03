package com.programmers.pcquotation.domain.comment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record CommentInfoResponse(

	@NonNull
	Long id,

	@NonNull
	Integer estimateId,

	@NonNull
	Long customerId,
	@NonNull
	Long sellerId,

	@NotBlank
	String content,

	@NotEmpty
	LocalDateTime createDate
) {
}
