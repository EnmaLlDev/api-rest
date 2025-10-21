package com.fp.api_rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

@GetMapping("/home")
    public String homePath() {
        String messageHardcode= "API REST by UDAM2 PROJECT. Developed by: ";
        String authorName= "Enmauel Lledo";
        return messageHardcode + authorName;
    }
}
