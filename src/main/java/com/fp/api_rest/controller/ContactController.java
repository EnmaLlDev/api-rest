package com.fp.api_rest.controller;

import com.fp.api_rest.model.Contact;
import com.fp.api_rest.model.dto.ContactMessageDTO;
import com.fp.api_rest.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para la gestión de mensajes de contacto.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Crea un nuevo mensaje de contacto.
     * @param contactDTO datos del contacto
     * @return mensaje de éxito o error
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createContact(@RequestBody ContactMessageDTO contactDTO) {
        logger.info("Contacto recibido: nombre='{}', apellido='{}', email='{}'",
                   contactDTO.getNombre(), contactDTO.getApellido(), contactDTO.getEmail());

        try {
            // Guardar en la base de datos
            Contact savedContact = contactService.saveContactMessage(contactDTO);

            Map<String, Object> body = new HashMap<>();
            body.put("status", "success");
            body.put("message", "Mensaje guardado correctamente");
            body.put("id", savedContact.getId());
            body.put("data", savedContact);

            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al guardar el contacto", e);
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("status", "error");
            errorBody.put("message", "Error al guardar el mensaje");
            return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener todos los mensajes de contacto.
     * GET /api/contact/all
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllContacts() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "success");
        body.put("data", contactService.getAllContacts());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener mensajes no revisados.
     * GET /api/contact/unreviewed
     */
    @GetMapping("/unreviewed")
    public ResponseEntity<Map<String, Object>> getUnreviewedContacts() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "success");
        body.put("data", contactService.getUnreviewedContacts());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    /**
     * Endpoint para marcar un mensaje como revisado.
     * PUT /api/contact/{id}/mark-reviewed
     */
    @PutMapping("/{id}/mark-reviewed")
    public ResponseEntity<Map<String, Object>> markAsReviewed(@PathVariable Long id) {
        Contact updated = contactService.markAsReviewed(id);
        Map<String, Object> body = new HashMap<>();
        if (updated != null) {
            body.put("status", "success");
            body.put("message", "Mensaje marcado como revisado");
            body.put("data", updated);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            body.put("status", "error");
            body.put("message", "Contacto no encontrado");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }
}

