package com.programmers.pcquotation.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerLoginRequest {
    private String username;
    private String password;
}