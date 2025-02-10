package com.programmers.pcquotation.domain.comment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.comment.dto.CommentCreateRequest;
import com.programmers.pcquotation.domain.comment.dto.CommentCreateResponse;
import com.programmers.pcquotation.domain.comment.dto.CommentDeleteResponse;
import com.programmers.pcquotation.domain.comment.dto.CommentInfoResponse;
import com.programmers.pcquotation.domain.comment.dto.CommentUpdateRequest;
import com.programmers.pcquotation.domain.comment.dto.CommentUpdateResponse;
import com.programmers.pcquotation.domain.comment.emtity.Comment;
import com.programmers.pcquotation.domain.comment.exception.CommentNotFoundException;
import com.programmers.pcquotation.domain.comment.repository.CommentRepository;
import com.programmers.pcquotation.domain.customer.entity.Customer;
import com.programmers.pcquotation.domain.customer.repository.CustomerRepository;
import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import com.programmers.pcquotation.domain.estimate.repository.EstimateRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final EstimateRepository estimateRepository;
	private final CustomerRepository customerRepository;

	@Transactional
	public CommentCreateResponse addComment(final CommentCreateRequest request) {
		Estimate estimate = estimateRepository.findById(request.estimateId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 견적 ID입니다."));
		Customer customer = customerRepository.findById(request.authorId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 작성자 ID입니다."));

		Comment comment = Comment.builder()
			.estimate(estimate)
			.author(customer)
			.content(request.content())
			.createDate(LocalDateTime.now())
			.build();

		Comment savedComment = commentRepository.save(comment);

		return new CommentCreateResponse(savedComment.getId(), "댓글 생성 완료");
	}

	@Transactional
	public List<CommentInfoResponse> getCommentList() {
		List<Comment> comments = commentRepository.findAll();
		return comments.stream()
			.map(comment -> new CommentInfoResponse(
				comment.getId(),
				comment.getEstimate().getId(),
				comment.getAuthor().getId(),
				comment.getContent(),
				comment.getCreateDate()
			))
			.collect(Collectors.toList());

	}

	@Transactional
	public CommentUpdateResponse updateComment(Long id, CommentUpdateRequest request) {
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException(id));

		Integer estimateId = comment.getEstimate().getId();
		Estimate estimate = estimateRepository.findById(estimateId)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 견적 ID입니다."));

		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 작성자 ID입니다."));

		comment.updateComment(
			estimate,
			customer,
			request.content()
		);

		return new CommentUpdateResponse(id, "댓글 수정 완료");

	}

	@Transactional
	public CommentDeleteResponse deleteComment(Long id) {
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException(id));

		commentRepository.delete(comment);

		return new CommentDeleteResponse(id, "댓글 삭제 완료");
	}
}
