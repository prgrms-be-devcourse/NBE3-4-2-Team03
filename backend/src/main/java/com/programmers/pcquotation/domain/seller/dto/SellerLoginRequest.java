package com.programmers.pcquotation.domain.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellerLoginRequest {
    private String username;
    private String password;
}