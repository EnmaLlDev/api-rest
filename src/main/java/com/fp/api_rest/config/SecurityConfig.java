package com.fp.api_rest.config;

import com.fp.api_rest.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad: filtros JWT, autorización por roles y codificación de contraseñas.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor con las dependencias de seguridad.
     * @param jwtAuthenticationFilter filtro de autenticación JWT
     * @param customUserDetailsService servicio de detalles de usuario
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configura las reglas de autorización y el filtro JWT.
     * @param http configuración de seguridad HTTP
     * @return cadena de filtros de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(customUserDetailsService)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"No autenticado\"}");
                        })
                        .accessDeniedHandler((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Sin permisos\"}");
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/refresh", "/auth/generate-hash", "/auth/logout").permitAll()
                        .requestMatchers(
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "/webjars/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/public/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/contact/create").permitAll()
                        .requestMatchers("/api/doctor/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers("/api/patient/me").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/api/patient/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers("/api/appointment/my").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/api/appointment/**").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/api/details/my").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/api/details/**").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/api/admin/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Expone el AuthenticationManager de Spring Security.
     * @param configuration configuración de autenticación
     * @return gestor de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Proporciona el codificador de contraseñas BCrypt.
     * @return codificador de contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}