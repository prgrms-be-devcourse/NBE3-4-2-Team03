package com.programmers.pcquotation.domain.seller.dto;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.pcquotation.global.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerLoginResponse {
    @NonNull
    String apiKey;
    @NonNull
    String accessToken;
    @NonNull
    UserType userType;
    @NonNull
    String message;
}
