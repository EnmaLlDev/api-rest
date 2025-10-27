package com.fp.api_rest.controller.files;

import com.fp.api_rest.convertFiles.ConvertMain;
import com.fp.api_rest.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importación necesaria
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/convert")
public class filesController { // 1. Renombrar la clase a "FilesController" (convención)

    @Autowired
    private ConvertMain convertMain;

    private static final String URL_EXPORT = "C:/Users/34633/IdeaProjects/api-rest/api-rest/src/main/resources/ficheros";

    // --- Importación de Pacientes ---

    @PostMapping("/patient/csv/import") // 3. Renombrar el endpoint para mayor claridad (import/export)
    public ResponseEntity<String> importPatientsCsv() {
        // 4. Mejorar: Esta ruta está 'hardcodeada'. Lo ideal es recibir el archivo por MultipartFile.
        String absolutePath = "C:/Users/34633/IdeaProjects/api-rest/api-rest/src/main/resources/ficheros/prueba.csv";

        try {
            System.out.println("Iniciando importación desde: " + absolutePath);
            List<Patient> savedPatients = convertMain.convertAndSavePatients(absolutePath);

            // 5. Corrección de la respuesta de éxito: Usar .size() para mostrar el número.
            String message = String.format("Importación exitosa. Se han guardado %d paciente(s) en la base de datos.", savedPatients.size());

            // 6. Usar HttpStatus.CREATED (201) si se están creando recursos en la DB
            return new ResponseEntity<>(message, HttpStatus.CREATED);

        } catch (IOException e) {
            System.err.println("Error I/O en la importación: " + e.getMessage());
            // 7. Usar HttpStatus.BAD_REQUEST (400) si el error es del archivo que envía el cliente (ej. no existe o formato incorrecto).
            return new ResponseEntity<>("Fallo al leer o procesar el archivo CSV. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Captura errores de DB, parseo de datos, etc.
            System.err.println("Error inesperado al guardar datos: " + e.getMessage());
            // 8. Usar HttpStatus.INTERNAL_SERVER_ERROR (500) para errores internos no previstos.
            return new ResponseEntity<>("Error interno inesperado al guardar datos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- Exportación de Pacientes ---

    @GetMapping("/patients/export/{fileName}")
    public ResponseEntity<String> exportPatientsToCsv(@PathVariable String fileName) {

        // 9. Uso directo y más simple de Path
        String fullPath = URL_EXPORT + "/" + fileName + ".csv";

        try {
            String resultMessage = convertMain.convertPatientsToCsvFile(fullPath);

            // La respuesta de éxito ya estaba correcta, se mantiene.
            return ResponseEntity.ok("Exportación iniciada correctamente. " + resultMessage + " Archivo guardado en: " + fullPath);

        } catch (IOException e) {
            System.err.println("Error de I/O durante la exportación: " + e.getMessage());
            // Uso de HttpStatus.INTERNAL_SERVER_ERROR (500) para errores de I/O en el servidor.
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