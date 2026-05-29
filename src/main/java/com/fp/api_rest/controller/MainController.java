package com.fp.api_rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador principal para endpoints de bienvenida.
 */
@RestController
public class MainController {

    /**
     * Devuelve un mensaje de bienvenida de la API.
     * @return mensaje con el autor del proyecto
     */
    @GetMapping("/home")
    public String homePath() {
        String messageHardcode= "API REST by UDAM2 PROJECT. Developed by: ";
        String authorName= "Enmanuel Lledo";
        return messageHardcode + authorName;
    }
}
