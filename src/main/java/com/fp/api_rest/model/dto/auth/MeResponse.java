package com.fp.api_rest.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MeResponse {
    private String username;
    private List<String> roles;
}
