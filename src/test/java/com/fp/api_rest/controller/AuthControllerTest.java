package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.auth.AuthResponse;
import com.fp.api_rest.model.dto.auth.LoginRequest;
import com.fp.api_rest.model.dto.auth.MeResponse;
import com.fp.api_rest.model.dto.auth.RefreshRequest;
import com.fp.api_rest.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController controller;

    @Test
    void loginReturnsAuthResponse() {
        when(authService.login(any(LoginRequest.class))).thenReturn(sampleAuthResponse());

        ResponseEntity<AuthResponse> response = controller.login(sampleLoginRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        AuthResponse body = response.getBody();
        assertEquals("admin", body.getUsername());
        assertEquals("Bearer", body.getTokenType());
    }

    @Test
    void loginReturnsUnauthorizedWhenServiceFails() {
        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("bad credentials"));

        ResponseEntity<AuthResponse> response = controller.login(sampleLoginRequest());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void refreshReturnsNewTokens() {
        when(authService.refresh(any(RefreshRequest.class))).thenReturn(sampleAuthResponse());

        ResponseEntity<AuthResponse> response = controller.refresh(sampleRefreshRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        AuthResponse body = response.getBody();
        assertEquals("refresh-456", body.getRefreshToken());
    }

    @Test
    void meReturnsUserInfo() {
        when(authService.me("admin")).thenReturn(sampleMeResponse());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");
        ResponseEntity<MeResponse> response = controller.me(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        MeResponse body = response.getBody();
        assertEquals("admin", body.getUsername());
    }

    @Test
    void logoutReturnsNoContentAndInvokesService() {
        doNothing().when(authService).logout("refresh-123");

        ResponseEntity<Void> response = controller.logout(sampleRefreshRequest());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authService).logout("refresh-123");
    }

    @Test
    void generateHashReturnsPasswordHashAndSql() {
        ResponseEntity<java.util.Map<String, String>> response = controller.generateHash(java.util.Map.of("password", "ChangeMe123!"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ChangeMe123!", response.getBody().get("password"));
        assertTrue(response.getBody().get("sql").startsWith("UPDATE users SET password = '"));
    }

    private AuthResponse sampleAuthResponse() {
        return AuthResponse.builder()
                .accessToken("access-123")
                .refreshToken("refresh-456")
                .tokenType("Bearer")
                .expiresInMs(900000L)
                .username("admin")
                .roles(List.of("ROLE_ADMIN"))
                .build();
    }

    private MeResponse sampleMeResponse() {
        return MeResponse.builder()
                .id(1L)
                .username("admin")
                .roles(List.of("ROLE_ADMIN"))
                .build();
    }

    private LoginRequest sampleLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("ChangeMe123!");
        return request;
    }

    private RefreshRequest sampleRefreshRequest() {
        RefreshRequest request = new RefreshRequest();
        request.setRefreshToken("refresh-123");
        return request;
    }
}
