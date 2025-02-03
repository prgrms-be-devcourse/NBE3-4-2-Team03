package com.programmers.pcquotation.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/h2-console/**").permitAll()
					.anyRequest().permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
