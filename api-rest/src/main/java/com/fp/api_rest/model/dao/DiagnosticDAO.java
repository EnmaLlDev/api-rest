package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Diagnostic;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface DiagnosticDAO extends BaseDAO<Diagnostic, Integer> {

    List<Diagnostic> findByDescriptionContainingIgnoreCase(String description);
}
