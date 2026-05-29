package com.fp.api_rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fp.api_rest.config.ApiDocsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para exponer la documentación de la API.
 */
@RestController
@RequestMapping("/api")
public class ApiDocsController {

    private static final Logger log = LoggerFactory.getLogger(ApiDocsController.class);

    private final ApiDocsService apiDocsService;

    public ApiDocsController(ApiDocsService apiDocsService) {
        this.apiDocsService = apiDocsService;
    }

    /**
     * Devuelve la documentación de la API en formato JSON.
     * @return documentación de la API
     */
    @GetMapping("/docs")
    public ResponseEntity<JsonNode> getApiDocs() {
        JsonNode node = apiDocsService.getApiDocs();
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(node);
    }

    /**
     * Endpoint para recargar manualmente el fichero (útil en desarrollo).
     */
    @PostMapping("/docs/reload")
    public ResponseEntity<String> reload() {
        apiDocsService.loadApiDocs();
        return ResponseEntity.ok("api-docs recargado");
    }

}

