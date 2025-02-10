package com.programmers.pcquotation.domain.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.pcquotation.domain.comment.dto.CommentCreateRequest;
import com.programmers.pcquotation.domain.comment.dto.CommentCreateResponse;
import com.programmers.pcquotation.domain.comment.dto.CommentInfoResponse;
import com.programmers.pcquotation.domain.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimates/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public CommentCreateResponse createComment(
		@RequestBody CommentCreateRequest request
	) {
		return commentService.addComment(request);
	}

	@GetMapping
	public List<CommentInfoResponse> getCommentList() {
		return commentService.getCommentList();
	}

}
