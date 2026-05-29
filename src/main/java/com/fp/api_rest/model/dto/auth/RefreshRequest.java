package com.fp.api_rest.model.dto.auth;

import lombok.Data;

@Data
/**
 * DTO que representa la petición de renovación del token de acceso.
 */
public class RefreshRequest {
    private String refreshToken;
}
