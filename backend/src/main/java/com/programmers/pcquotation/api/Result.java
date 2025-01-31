package com.programmers.pcquotation.api;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//결과 반환용
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result<T> {

	@NonNull
	private int statusCode;

	@NonNull
	private String message;

	private T data;

	public static <T> Result<T> success(final T data) {
		return createResult(200, "Success", data);
	}

	public static <T> Result<T> error(final int statusCode, final String message) {
		return createResult(statusCode, message);
	}

	public static <T> Result<T> createResult(final int statusCode, final String message,
		final T data) {
		return Result.<T>builder()
			.statusCode(statusCode)
			.message(message)
			.data(data)
			.build();
	}

	public static <T> Result<T> createResult(final int statusCode, final String message) {
		return Result.<T>builder()
			.statusCode(statusCode)
			.message(message)
			.build();
	}
}