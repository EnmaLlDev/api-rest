package com.fp.api_rest.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.charset.MalformedInputException;

@Service
public class ApiDocsService implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApiDocsService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private volatile JsonNode apiDocs;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loadApiDocs();
    }

    public synchronized void loadApiDocs() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("api-docs.json")) {
            if (is != null) {
                apiDocs = objectMapper.readTree(is);
                return;
            }
        } catch (IOException e) {
            log.debug("Error leyendo api-docs.json desde classpath", e);
        }

        Path path = Paths.get("api-docs.json");
        if (Files.exists(path)) {
            try {
                apiDocs = objectMapper.readTree(Files.newBufferedReader(path, StandardCharsets.UTF_8));
                log.info("api-docs.json cargado desde: {} (UTF-8)", path.toAbsolutePath());
            } catch (MalformedInputException e) {
                // Intenta con UTF-16 si falla UTF-8
                log.debug("UTF-8 fallo, intentando UTF-16 para api-docs.json");
                try {
                    apiDocs = objectMapper.readTree(Files.newBufferedReader(path, StandardCharsets.UTF_16));
                    log.info("api-docs.json cargado desde: {} (UTF-16)", path.toAbsolutePath());
                } catch (IOException e2) {
                    log.warn("No se pudo cargar api-docs.json con UTF-16 - archivo puede estar corrupto");
                }
            } catch (IOException e) {
                log.warn("Error leyendo api-docs.json desde filesystem {}", path.toAbsolutePath());
            }
        } else {
            log.debug("No se encontró api-docs.json en classpath ni en {}", path.toAbsolutePath());
        }
    }

    public JsonNode getApiDocs() {
        return apiDocs;
    }

}

