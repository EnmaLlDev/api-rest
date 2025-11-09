package com.fp.api_rest.convertFiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "csv")
public class CsvProperties {

    private final Rutas ruta = new Rutas();
    private final Parser parser = new Parser();

    public Rutas getRuta() { return ruta; }
    public Parser getParser() { return parser; }

    @Data
    public static class Rutas {
        private String base;
        private String inputFileName;
        private String outputFileName;
    }

    @Data
    public static class Parser {
        private String delimiter;
        private boolean skipHeaders;
        private String encoding;
    }
}