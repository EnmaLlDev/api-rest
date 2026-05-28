package com.fp.api_rest.service;

import com.fp.api_rest.model.Contact;
import com.fp.api_rest.model.dto.ContactMessageDTO;
import com.fp.api_rest.repository.dao.ContactDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    /**
     * Guarda un nuevo mensaje de contacto en la base de datos.
     * @param dto el DTO con los datos del contacto
     * @return el Contact guardado con la BD
     */
    public Contact saveContactMessage(ContactMessageDTO dto) {
        Contact contact = Contact.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .mensaje(dto.getMensaje())
                .revisado(false)
                .build();
        return contactDAO.save(contact);
    }

    /**
     * Obtiene todos los mensajes de contacto.
     * @return lista de todos los Contact
     */
    public List<Contact> getAllContacts() {
        return contactDAO.findAll();
    }

    /**
     * Obtiene un mensaje de contacto por ID.
     * @param id el ID del contacto
     * @return el Contact si existe
     */
    public Optional<Contact> getContactById(Long id) {
        return contactDAO.findById(id);
    }

    /**
     * Obtiene todos los mensajes no revisados.
     * @return lista de Contact con revisado = false
     */
    public List<Contact> getUnreviewedContacts() {
        return contactDAO.findByRevisado(false);
    }

    /**
     * Marca un mensaje como revisado.
     * @param id el ID del contacto
     * @return el Contact actualizado
     */
    public Contact markAsReviewed(Long id) {
        Optional<Contact> contact = contactDAO.findById(id);
        if (contact.isPresent()) {
            Contact c = contact.get();
            c.setRevisado(true);
            return contactDAO.save(c);
        }
        return null;
    }

    /**
     * Elimina un mensaje de contacto.
     * @param id el ID del contacto
     */
    public void deleteContact(Long id) {
        contactDAO.deleteById(id);
    }
}

