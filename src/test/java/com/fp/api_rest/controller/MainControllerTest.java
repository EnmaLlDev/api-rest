package com.fp.api_rest.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainControllerTest {

    @Test
    void homePathReturnsHardcodedMessage() {
        MainController controller = new MainController();

        assertEquals("API REST by UDAM2 PROJECT. Developed by: Enmanuel Lledo", controller.homePath());
    }
}

