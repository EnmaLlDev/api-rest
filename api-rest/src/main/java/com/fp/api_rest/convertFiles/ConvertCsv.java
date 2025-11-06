package com.fp.api_rest.convertFiles;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ConvertCsv {

    @Autowired
    private PatientDAO patientDAO;

    private static final String[] CSV_HEADERS = {"dni", "firstname", "secondname", "lastname", "secondlastname", "email", "phone", "birthdate", "adress"};
    @Transactional
    public List<Patient> convertAndSavePatients(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(CSV_HEADERS)
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = format.parse(reader)) { // Usamos el objeto 'format'

            for (CSVRecord csvRecord : csvParser) {
                patients.add(mapCsvRecordToPatient(csvRecord));
            }
            return patientDAO.saveAll(patients);

        } catch (IOException e) {
            throw new IOException("Error al procesar el fichero CSV: " + e.getMessage(), e);
        }
    }

    private Patient mapCsvRecordToPatient(CSVRecord record) {
        Patient patient = new Patient();
        patient.setDni(record.get("dni"));
        patient.setFirstName(record.get("firstname"));
        patient.setSecondName(record.get("secondname"));
        patient.setLastName(record.get("lastname"));
        patient.setSecondLastName(record.get("secondlastname"));
        patient.setEmail(record.get("email"));
        patient.setPhone(record.get("phone"));
        String birthDateStr = record.get("birthdate");
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        patient.setBirthDate(birthDate);
        patient.setAddress(record.get("adress"));

        patient.setBirthDate(birthDate);

        return patient;
    }

    @Transactional(readOnly = true)
    public String convertPatientsToCsvFile(String filePath) throws  IOException {
        List<Patient> patients = patientDAO.findAll();

        if (patients.isEmpty()) {return "Tabla de pacientes vacia.";}

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(CSV_HEADERS)
                .setTrim(true)
                .build();

        try (FileWriter out = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(out, format)) {

            for (Patient patient : patients) {
                printer.printRecord(
                        patient.getDni(),
                        patient.getFirstName(),
                        patient.getSecondName(),
                        patient.getLastName(),
                        patient.getSecondLastName(),
                        patient.getEmail(),
                        patient.getPhone(),
                        patient.getBirthDate().toString(),
                        patient.getAddress()
                );
            }
            return "Archivo CSV generado exitosamente en: " + filePath;
        } catch (IOException e) {
            throw new IOException("Error al escribir el fichero CSV: " + e.getMessage(), e);
        }
    }
}
