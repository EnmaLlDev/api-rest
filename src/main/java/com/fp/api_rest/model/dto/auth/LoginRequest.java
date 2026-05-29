package com.fp.api_rest.model.dto.auth;

import lombok.Data;

@Data
/**
 * DTO que representa la petición de inicio de sesión.
 */
public class LoginRequest {
    private String username;
    private String password;
}
