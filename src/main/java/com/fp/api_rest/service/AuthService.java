package com.fp.api_rest.service;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import com.fp.api_rest.model.dto.auth.*;
import com.fp.api_rest.repository.dao.UserDAO;
import com.fp.api_rest.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de autenticación y autorización de usuarios.
 */
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

    /**
     * Autentica un usuario y genera tokens de acceso y refresco.
     * @param request credenciales de inicio de sesión
     * @return respuesta con tokens y datos del usuario
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        logger.info("Intento de login para usuario: {}", request.getUsername());

        try {
            // Autenticar (carga el usuario desde BD automáticamente)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            logger.info("Autenticación exitosa para: {}", request.getUsername());

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UserDetails userDetails = userPrincipal;

            // Obtener User entity directamente del UserPrincipal (sin segunda consulta a BD)
            User user = userPrincipal.getUser();

            String accessToken = jwtService.generateAccessToken(userDetails);
            RefreshToken refreshToken = refreshTokenService.createToken(user);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .tokenType("Bearer")
                    .expiresInMs(900000L)
                    .username(userDetails.getUsername())
                    .roles(userDetails.getAuthorities().stream()
                            .map(auth -> auth.getAuthority())
                            .toList())
                    .build();
        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para usuario: {}", request.getUsername());
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        } catch (Exception e) {
            logger.error("Error durante autenticación: ", e);
            throw new BadCredentialsException("Error en la autenticación");
        }
    }

    /**
     * Renueva el access token a partir de un refresh token válido.
     * @param request solicitud con el refresh token
     * @return respuesta con nuevo access token y refresh token rotado
     */
    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken stored = refreshTokenService.verifyUsableToken(request.getRefreshToken());
        User user = stored.getUser();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);

        refreshTokenService.revokeToken(stored.getToken());
        RefreshToken rotated = refreshTokenService.createToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(rotated.getToken())
                .tokenType("Bearer")
                .expiresInMs(900000L)
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .toList())
                .build();
    }

    /**
     * Obtiene la información del usuario autenticado.
     * @param username nombre de usuario
     * @return datos del usuario y sus roles
     */
    @Transactional
    public MeResponse me(String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        List<String> roles = user.getRoles().stream().map(r -> r.getName()).toList();
        return MeResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    /**
     * Cierra la sesión revocando el refresh token.
     * @param refreshToken token de refresco a revocar
     */
    public void logout(String refreshToken) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            refreshTokenService.revokeToken(refreshToken);
        }
    }
}
