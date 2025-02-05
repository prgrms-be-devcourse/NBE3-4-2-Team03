package com.programmers.pcquotation.domain.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}