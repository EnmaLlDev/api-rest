package com.fp.api_rest.repository;

import com.fp.api_rest.model.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, Long> {

}
