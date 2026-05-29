package com.fp.api_rest.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class OpenBrowserOnStartup {

    @EventListener(ApplicationReadyEvent.class)
    public void openSwaggerOnStartup() {
        String url = "http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs";

        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new URI(url));
                return;
            }
        } catch (Exception e) {
            System.out.println("Desktop.browse failed: " + e.getMessage());
        }

        try {
            String[] cmd = {"cmd", "/c", "start", "", url};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("Could not open browser automatically: " + e.getMessage());
        }
    }
}

