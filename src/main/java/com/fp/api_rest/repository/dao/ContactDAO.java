package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Contact (mensajes de contacto).
 */
public interface ContactDAO extends JpaRepository<Contact, Long> {
    /**
     * Busca mensajes según si han sido revisados.
     * @param revisado true si han sido revisados
     * @return lista de mensajes coincidentes
     */
    List<Contact> findByRevisado(Boolean revisado);
    /**
     * Busca un mensaje de contacto por su correo electrónico.
     * @param email correo electrónico
     * @return contacto encontrado, si existe
     */
    Optional<Contact> findByEmail(String email);
    /**
     * Busca mensajes de contacto por apellido.
     * @param apellido apellido a buscar
     * @return lista de contactos coincidentes
     */
    List<Contact> findByApellido(String apellido);
}

