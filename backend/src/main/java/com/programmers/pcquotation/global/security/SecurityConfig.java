package com.programmers.pcquotation.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomAuthenticationFilter customAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers(HttpMethod.GET, "/sellers/api/{code}")
					.hasRole("SELLER")
					.requestMatchers(HttpMethod.GET, "/sellers")
					.hasRole("SELLER")
					.anyRequest()
					.permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(
				exceptionHandling -> exceptionHandling
					.authenticationEntryPoint(
						(request, response, authException) -> {
							response.setContentType("application/json;charset=UTF-8");
							response.setStatus(401);
							response.getWriter().write(
								"사용자 인증정보가 올바르지 않습니다."
							);
						}
					)
					.accessDeniedHandler(
						(request, response, accessDeniedException) -> {
							response.setContentType("application/json;charset=UTF-8");

							response.setStatus(403);
							response.getWriter().write(
								"권한이 없습니다."

							);
						}
					)
			);

		return http.build();
	}

}
