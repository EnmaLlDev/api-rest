package com.fp.api_rest.service;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import com.fp.api_rest.model.dto.auth.*;
import com.fp.api_rest.repository.dao.UserDAO;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDAO userDAO;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDAO userDAO,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService,
                       CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDAO = userDAO;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDAO.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresInMs(900000L)
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(r -> r.getName()).toList())
                .build();
    }

    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken stored = refreshTokenService.verifyUsableToken(request.getRefreshToken());
        User user = stored.getUser();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);

        stored.setRevoked(true);
        refreshTokenService.revokeToken(stored.getToken());
        RefreshToken rotated = refreshTokenService.createToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(rotated.getToken())
                .tokenType("Bearer")
                .expiresInMs(900000L)
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(r -> r.getName()).toList())
                .build();
    }

    public MeResponse me(String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        List<String> roles = user.getRoles().stream().map(r -> r.getName()).toList();
        return MeResponse.builder()
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    public void logout(String refreshToken) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            refreshTokenService.revokeToken(refreshToken);
        }
    }
}
