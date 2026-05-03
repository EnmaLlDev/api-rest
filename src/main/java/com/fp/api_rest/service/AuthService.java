package com.fp.api_rest.service;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import com.fp.api_rest.model.dto.auth.*;
import com.fp.api_rest.repository.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    // LOGs para trackear la salida del endpoint
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
        logger.info("Intento de login para usuario: {}", request.getUsername());

        try {
            // Verificar que el usuario existe en la BD
            User user = userDAO.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        logger.warn("Usuario no encontrado: {}", request.getUsername());
                        return new BadCredentialsException("Usuario o contraseña inválidos");
                    });

            logger.debug("Usuario encontrado: {}", user.getUsername());

            // Autenticar
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            logger.info("Autenticación exitosa para: {}", request.getUsername());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

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
        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para usuario: {}", request.getUsername());
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        } catch (Exception e) {
            logger.error("Error durante autenticación: ", e);
            throw new BadCredentialsException("Error en la autenticación");
        }
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
