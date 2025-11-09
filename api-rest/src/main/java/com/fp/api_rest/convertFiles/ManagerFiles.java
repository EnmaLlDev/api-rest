package com.fp.api_rest.convertFiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("files")
public class ManagerFiles {

    private final CsvProperties csvProperties;

    public ManagerFiles(CsvProperties csvProperties) {
        this.csvProperties = csvProperties;
    }

    public void importarDatosDesdeCsv() {
        String fullPath = csvProperties.getRuta().getBase() + csvProperties.getRuta().getInputFileName();
        String delimiter = csvProperties.getParser().getDelimiter();

        System.out.println("Iniciando Sesión CSV. Archivo: " + fullPath + " Delimitador: " + delimiter);

        // 2. Usar la lógica de ConvertCsv/ManagerCsv

        // convertCsv.read(fullPath, delimiter, ...);


        // 3. Pasar los datos leídos a ManagerJDBC o repository.dao para la Sesión DB
        // managerJDBC.saveAll(datosLeidos);
    }
}