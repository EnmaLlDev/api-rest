package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContactDAO extends JpaRepository<Contact, Long> {
    List<Contact> findByRevisado(Boolean revisado);
    Optional<Contact> findByEmail(String email);
    List<Contact> findByApellido(String apellido);
}

