package com.fp.api_rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO que representa un mensaje de contacto para transferencia de datos.
 */
public class ContactMessageDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String mensaje;
    private Boolean revisado;
}

