package com.fp.api_rest.service;

import com.fp.api_rest.model.Diagnostic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticService {
    @Autowired
    private final DiagnosticDAO diagnosticDAO;

    public List<Diagnostic> findAll() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        return diagnostics;
    }

    public Diagnostic save(Diagnostic diagnostic) {
        return diagnostic;
    }
}

