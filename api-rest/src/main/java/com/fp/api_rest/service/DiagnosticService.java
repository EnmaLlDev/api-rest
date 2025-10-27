package com.fp.api_rest.service;

import com.fp.api_rest.model.Diagnostic;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.DiagnosticDAO;
import com.fp.api_rest.model.dto.DiagnosticDTO;
import com.fp.api_rest.model.dto.mapper.DiagnosticMapper;
import com.fp.api_rest.model.dto.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosticService {

    @Autowired
    private final DiagnosticDAO diagnosticDAO;

    public DiagnosticService(DiagnosticDAO diagnosticDAO) {
        this.diagnosticDAO = diagnosticDAO;
    }

    public List<DiagnosticDTO> findAll() {
        return diagnosticDAO.findAll()
                .stream()
                .map(DiagnosticMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticDTO findById(Integer id) {
        return diagnosticDAO.findById(id)
                .map(DiagnosticMapper::toDTO)
                .orElse(null);
    }

    public DiagnosticDTO save(DiagnosticDTO dto) {
        Diagnostic diagnostic = DiagnosticMapper.toEntity(dto);
        Diagnostic saved = diagnosticDAO.save(diagnostic);
        return DiagnosticMapper.toDTO(saved);
    }

    public void deleteById(Integer id) {
        diagnosticDAO.deleteById(id);
    }

}

