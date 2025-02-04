package com.programmers.pcquotation.global.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import com.programmers.pcquotation.domain.sellers.service.SellersService;
import com.programmers.pcquotation.global.rq.Rq;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
	private final SellersService sellersService;
	private final Rq rq;

	record AuthTokens(
		String apiKey,
		String accessToken
	) {
	}

	private AuthTokens getAuthTokensFromRequest() {
		String authorization = rq.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer ")) {
			String token = authorization.substring("Bearer ".length());
			String[] tokenBits = token.split(" ", 2);

			if (tokenBits.length == 2)
				return new AuthTokens(tokenBits[0], tokenBits[1]);
		}

		String apiKey = rq.getCookieValue("apiKey");
		String accessToken = rq.getCookieValue("accessToken");

		if (apiKey != null && accessToken != null)
			return new AuthTokens(apiKey, accessToken);

		return null;
	}

	private void refreshAccessToken(Sellers sellers) {
		String newAccessToken = sellersService.getAccessToken(sellers);

		rq.setHeader("Authorization", "Bearer " + sellers.getApiKey() + " " + newAccessToken);
		rq.setCookie("accessToken", newAccessToken);
	}

	private Sellers refreshAccessTokenByApiKey(String apiKey) {
		Optional<Sellers> opMemberByApiKey = sellersService.findByApiKey(apiKey);

		if (opMemberByApiKey.isEmpty()) {
			return null;
		}

		Sellers sellers = opMemberByApiKey.get();

		refreshAccessToken(sellers);

		return sellers;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		if (List.of("/sellers/login").contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		AuthTokens authTokens = getAuthTokensFromRequest();

		if (authTokens == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String apiKey = authTokens.apiKey;
		String accessToken = authTokens.accessToken;

		Sellers member = sellersService.getMemberFromAccessToken(accessToken);

		if (member == null)
			member = refreshAccessTokenByApiKey(apiKey);

		if (member != null)
			rq.setLogin(member);

		filterChain.doFilter(request, response);
	}

}
