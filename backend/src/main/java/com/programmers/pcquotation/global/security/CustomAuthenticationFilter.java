package com.programmers.pcquotation.global.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.programmers.pcquotation.domain.seller.entitiy.Seller;
import com.programmers.pcquotation.domain.seller.service.SellerService;
import com.programmers.pcquotation.global.rq.Rq;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
	private final SellerService sellerService;
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

	private void refreshAccessToken(Seller seller) {
		String newAccessToken = sellerService.getAccessToken(seller);

		rq.setHeader("Authorization", "Bearer " + seller.getApiKey() + " " + newAccessToken);
		rq.setCookie("accessToken", newAccessToken);
	}

	private Seller refreshAccessTokenByApiKey(String apiKey) {
		Optional<Seller> opMemberByApiKey = sellerService.findByApiKey(apiKey);

		if (opMemberByApiKey.isEmpty()) {
			return null;
		}

		Seller seller = opMemberByApiKey.get();

		refreshAccessToken(seller);

		return seller;
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

		Seller member = sellerService.getMemberFromAccessToken(accessToken);

		if (member == null)
			member = refreshAccessTokenByApiKey(apiKey);

		if (member != null)
			rq.setLogin(member);

		filterChain.doFilter(request, response);
	}

}
