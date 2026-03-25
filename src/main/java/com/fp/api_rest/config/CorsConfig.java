package com.fp.api_rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") //Aplica a todos tus endpoints
                .allowedOrigins("*") //Acepta cualquier origen (emulador, dispositivo físico, etc.)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") //Acepta cualquier cabecera HTTP
                .maxAge(3600); //El navegador/cliente cachea el preflight 1 hora
    }
}

