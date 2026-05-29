package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.auth.*;
import com.fp.api_rest.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para la autenticación de usuarios.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Autentica un usuario y devuelve los tokens de sesión.
     * @param request credenciales de inicio de sesión
     * @return respuesta con tokens o 401
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Renueva el access token mediante un refresh token válido.
     * @param request solicitud con el refresh token
     * @return nueva respuesta con tokens
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    /**
     * Devuelve la información del usuario autenticado.
     * @param authentication autenticación del usuario
     * @return datos del usuario y sus roles
     */
    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        return ResponseEntity.ok(authService.me(authentication.getName()));
    }

    /**
     * Cierra la sesión revocando el refresh token.
     * @param request solicitud opcional con el refresh token
     * @return respuesta sin contenido
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody(required = false) RefreshRequest request) {
        if (request != null) {
            authService.logout(request.getRefreshToken());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint temporal para generar contraseñas hasheadas (ELIMINAR).
     * @param request mapa con la contraseña en texto plano
     * @return contraseña, hash y sentencia SQL de actualización
     */
    // Endpoint temporal para generar la contraseña hasheada (ELIMINAR)
    @PostMapping("/generate-hash")
    public ResponseEntity<Map<String, String>> generateHash(@RequestBody Map<String, String> request) {
        String password = request.get("password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        return ResponseEntity.ok(Map.of(
                "password", password,
                "hash", hash,
                "sql", "UPDATE users SET password = '" + hash + "' WHERE username IN ('admin', 'doctor', 'patient');"
        ));
    }
}
