package com.fp.api_rest.model.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
