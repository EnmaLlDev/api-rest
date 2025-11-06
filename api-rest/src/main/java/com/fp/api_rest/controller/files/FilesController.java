package com.fp.api_rest.controller.files;

import com.fp.api_rest.convertFiles.*;
import com.fp.api_rest.model.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/convert")
public class FilesController {

    private final ConvertCsv convertMain;
    private static final String URL_FILES = "C:/Users/34633/IdeaProjects/api-rest/api-rest/src/main/resources/ficheros";

    public FilesController(ConvertCsv convertMain) {
        this.convertMain = convertMain;
    }

    @PostMapping("/patient/import/{fileName}")
    public ResponseEntity<String> importPatientsCsv(@PathVariable String fileName) {

        String absolutePath = URL_FILES +  "/" + fileName + ".csv";

        try {
            System.out.println("Iniciando importación desde: " + absolutePath);
            List<Patient> savedPatients = convertMain.convertAndSavePatients(absolutePath);
            String message = String.format("Importación exitosa. Se han guardado %d paciente(s) en la base de datos.", savedPatients.size());

            return new ResponseEntity<>(message, HttpStatus.CREATED);

        } catch (IOException e) {
            System.err.println("Error I/O en la importación: " + e.getMessage());
            return new ResponseEntity<>("Fallo al leer o procesar el archivo CSV. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error inesperado al guardar datos: " + e.getMessage());
            return new ResponseEntity<>("Error interno inesperado al guardar datos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/patients/export/{fileName}")
    public ResponseEntity<String> exportPatientsToCsv(@PathVariable String fileName) {

        String fullPath = URL_FILES + "/" + fileName + ".csv";

        try {
            String resultMessage = convertMain.convertPatientsToCsvFile(fullPath);
            return ResponseEntity.ok("Exportación iniciada correctamente. " + resultMessage + " Archivo guardado en: " + fullPath);

        } catch (IOException e) {
            System.err.println("Error de I/O durante la exportación: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error del servidor al escribir el archivo CSV. Verifique la ruta y permisos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor durante la exportación.");
        }
    }
}