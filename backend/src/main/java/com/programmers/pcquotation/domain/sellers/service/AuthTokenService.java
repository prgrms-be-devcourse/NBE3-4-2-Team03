package com.programmers.pcquotation.domain.sellers.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import com.programmers.pcquotation.global.jwt.Jwt;

@Service
public class AuthTokenService {
	@Value("${jwt.secretKey}")
	private String jwtSecretKey;

	@Value("${jwt.accessToken.expirationSeconds}")
	private long accessTokenExpirationSeconds;

	String getAccessToken(Sellers sellers) {
		long id = sellers.getId();
		String username = sellers.getUsername();

		return Jwt.toString(
			jwtSecretKey,
			accessTokenExpirationSeconds,
			Map.of("id", id, "username", username)
		);
	}

	Map<String, Object> payload(String accessToken) {
		Map<String, Object> parsedPayload = Jwt.payload(jwtSecretKey, accessToken);

		if(parsedPayload == null) return null;

		long id = (long) (Integer) parsedPayload.get("id");
		String username = (String) parsedPayload.get("username");

		return Map.of("id", id, "username", username);
	}
}

