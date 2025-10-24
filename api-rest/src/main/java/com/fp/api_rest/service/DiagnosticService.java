package com.fp.api_rest.service;

import com.fp.api_rest.model.Diagnostic;
import com.fp.api_rest.model.dao.DiagnoticDAO;
import com.fp.api_rest.model.dto.DiagnosticDTO;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.dto.mapper.DiagnosticMapper;
import com.fp.api_rest.model.dto.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiagnosticService {

    @Autowired
    private final DiagnoticDAO diagnosticDAO;

    public DiagnosticService(DiagnoticDAO diagnosticDAO) {
        this.diagnosticDAO = diagnosticDAO;
    }

    public List<Diagnostic> findAll() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        return diagnostics;
    }

    public DiagnosticDTO findById(Integer id) {
        return diagnosticDAO.findById(id)
                .map(DiagnosticMapper:: toDTO)
                .orElse(null);
    }


    public Diagnostic save(Diagnostic diagnostic) {
        return diagnostic;
    }
}

