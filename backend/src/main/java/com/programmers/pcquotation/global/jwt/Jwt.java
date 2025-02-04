package com.programmers.pcquotation.global.jwt;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Jwt {
	public static String toString(String secret, long expireSeconds, Map<String, Object> body) {
		Date issuedAt = new Date();
		Date expiration = new Date(issuedAt.getTime() + 1000L * expireSeconds);

		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

		String jwt = Jwts.builder()
			.claims(body)
			.issuedAt(issuedAt)
			.expiration(expiration)
			.signWith(secretKey)
			.compact();

		return jwt;
	}
	public static boolean isValid(String secret, String jwtStr) {
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

		try {
			Jwts
				.parser()
				.verifyWith(secretKey)
				.build()
				.parse(jwtStr);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	public static Map<String, Object> payload(String secret, String jwtStr) {
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

		try {
			return (Map<String, Object>) Jwts
				.parser()
				.verifyWith(secretKey)
				.build()
				.parse(jwtStr)
				.getPayload();
		} catch (Exception e) {
			return null;
		}
	}

}
