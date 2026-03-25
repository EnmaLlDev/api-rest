package com.fp.api_rest.model.dto.auth;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
