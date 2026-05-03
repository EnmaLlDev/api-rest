package com.fp.api_rest.config;

import com.fp.api_rest.service.CustomUserDetailsService;
import com.fp.api_rest.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String requestPath = request.getRequestURI();

            // Si no hay header Authorization o no comienza con Bearer, continúa el filtro
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.debug("Sin token JWT para: {}", requestPath);
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.substring(7);

            try {
                String username = jwtService.extractUsername(jwt);
                logger.debug("Usuario extraído del JWT: {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        logger.debug("JWT válido para usuario: {}", username);
                        var authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        logger.warn("JWT inválido para usuario: {}", username);
                    }
                }
            } catch (Exception e) {
                logger.error("Error procesando JWT: {}", e.getMessage());
                // No lanzar excepción, permitir que continúe el filtro
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error en JwtAuthenticationFilter: {}", e.getMessage(), e);
            filterChain.doFilter(request, response);
        }
    }
}
