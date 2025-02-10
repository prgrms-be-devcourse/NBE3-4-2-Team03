package com.programmers.pcquotation.domain.comment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequest(

	@NotBlank
	String content,
	@NotBlank
	LocalDateTime createDate,
	@NotNull
	Integer estimateId,
	@NotNull
	Long authorId
) {
}
