package com.programmers.pcquotation.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomAuthenticationFilter customAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers(HttpMethod.GET, "/sellers")
								.hasRole("SELLER")
								.anyRequest()
								.permitAll()
				)
				.cors(cors -> corsConfigurationSource())
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

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://localhost:3000"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}