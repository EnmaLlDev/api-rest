package com.fp.api_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fp.api_rest.model.dto.auth.AuthResponse;
import com.fp.api_rest.model.dto.auth.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test end-to-end que valida el flujo completo de autenticación e integración de la API.
 * Este test arranca el contexto completo de Spring para verificar que todos los componentes
 * (controladores, servicios, seguridad, BD) funcionan juntos correctamente.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCompleteAuthenticationAndAccessFlow() throws Exception {
        // Paso 1: Intentar acceder a un recurso protegido sin token → debe retornar 401
        mockMvc.perform(get("/api/doctor/getAll"))
                .andExpect(status().isUnauthorized());

        // Paso 2: Login con credenciales válidas → obtener token JWT
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("ChangeMe123!");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.username").value("admin"))
                .andReturn();

        // Extraer el token JWT de la respuesta
        String responseBody = loginResult.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(responseBody, AuthResponse.class);
        String accessToken = authResponse.getAccessToken();
        assertNotNull(accessToken, "Access token no debe ser nulo");

        // Paso 3: Acceder a recursos protegidos usando el token JWT obtenido
        mockMvc.perform(get("/api/doctor/getAll")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Paso 4: Acceder a endpoint /auth/me para obtener información del usuario autenticado
        mockMvc.perform(get("/auth/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.roles").isArray());

        // Paso 5: Intentar acceder con un token inválido → debe retornar 401
        mockMvc.perform(get("/api/doctor/getAll")
                        .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isUnauthorized());

        // Paso 6: Logout revocando el refresh token
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                java.util.Map.of("refreshToken", authResponse.getRefreshToken()))))
                .andExpect(status().isNoContent());
    }

    @Test
    void testLoginFailureWithInvalidCredentials() throws Exception {
        // Intento de login con contraseña incorrecta
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("WrongPassword");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccessControlByRole() throws Exception {
        // Login como doctor (role DOCTOR)
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("doctor");
        loginRequest.setPassword("ChangeMe123!");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(responseBody, AuthResponse.class);
        String token = authResponse.getAccessToken();

        // Doctor puede acceder a /api/doctor
        mockMvc.perform(get("/api/doctor/getAll")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // Doctor puede acceder a /api/patient (según SecurityConfig)
        mockMvc.perform(get("/api/patient/getAll")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}

