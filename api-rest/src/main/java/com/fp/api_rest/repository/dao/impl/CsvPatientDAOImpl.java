package com.fp.api_rest.repository.dao.impl;

import com.fp.api_rest.convertFiles.CsvProperties;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@ConditionalOnProperty(name = "app.data.source", havingValue = "csv")
public class CsvPatientDAOImpl {
    private static final String[] HEADERS = {"dni", "firstname", "secondname", "lastname", "secondlastname", "email", "phone", "birthdate", "address"};
    private final CsvProperties props;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CsvPatientDAOImpl(CsvProperties props) {
        this.props = props;
    }

    private File resolveInputFile() {
        return new File(props.getRuta().getBase(), props.getRuta().getInputFileName());
    }

    private File resolveOutputFile() {
        return new File(props.getRuta().getBase(), props.getRuta().getOutputFileName());
    }

    private CSVFormat readFormat() {
        CSVFormat.Builder b = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setTrim(true);
        if (props.getParser().isSkipHeaders()) b.setSkipHeaderRecord(true);
        if (props.getParser().getDelimiter() != null) b.setDelimiter(props.getParser().getDelimiter().charAt(0));
        return b.build();
    }

    private CSVFormat writeFormat() {
        return CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setTrim(true)
                .build();
    }

    private Charset charset() {
        String enc = props.getParser().getEncoding();
        return enc != null ? Charset.forName(enc) : Charset.defaultCharset();
    }

    private Patient map(CSVRecord r) {
        Patient p = new Patient();
        p.setDni(r.get("dni"));
        p.setFirstName(r.get("firstname"));
        p.setSecondName(r.get("secondname"));
        p.setLastName(r.get("lastname"));
        p.setSecondLastName(r.get("secondlastname"));
        p.setEmail(r.get("email"));
        p.setPhone(r.get("phone"));
        p.setBirthDate(LocalDate.parse(r.get("birthdate")));
        p.setAddress(r.get("adress"));
        return p;
    }

    private void print(CSVPrinter printer, Patient p) throws IOException {
        printer.printRecord(
                p.getDni(), p.getFirstName(), p.getSecondName(), p.getLastName(),
                p.getSecondLastName(), p.getEmail(), p.getPhone(),
                p.getBirthDate() != null ? p.getBirthDate().toString() : "",
                p.getAddress()
        );
    }

    public Optional<Patient> findById(Integer integer) {
        return Optional.empty();
    }

    // ——— Implementación BaseDAO ———
    public List<Patient> findAll() {
        lock.readLock().lock();
        try (Reader in = new InputStreamReader(new FileInputStream(resolveInputFile()), charset());
             CSVParser parser = readFormat().parse(in)) {
            List<Patient> list = new ArrayList<>();
            for (CSVRecord r : parser) list.add(map(r));
            return list;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Optional<Patient> findById(Long id) {
        // Si el identificador real es el DNI, adapta la firma del DAO. Si usas Long, necesitarás una columna de id.
        return Optional.empty();
    }


    public List<Patient> findByAddressContaining(String address) {
        String needle = address == null ? "" : address.toLowerCase();
        List<Patient> out = new ArrayList<>();
        for (Patient p : findAll()) {
            if (p.getAddress() != null && p.getAddress().toLowerCase().contains(needle)) {
                out.add(p);
            }
        }
        return out;
    }

    public Patient save(Patient entity) {
        // Estrategia simple: reescribir fichero completo con colección actualizada
        List<Patient> all = new ArrayList<>(findAll());
        // deduplicar por DNI (o por el campo que decidas como PK)
        all.removeIf(p -> Objects.equals(p.getDni(), entity.getDni()));
        all.add(entity);
        writeAll(all);
        return entity;
    }

    public List<Patient> saveAll(List<Patient> entities) {
        List<Patient> base = new ArrayList<>(findAll());
        Map<String, Patient> byDni = new LinkedHashMap<>();
        for (Patient p : base) byDni.put(p.getDni(), p);
        for (Patient p : entities) byDni.put(p.getDni(), p);
        List<Patient> merged = new ArrayList<>(byDni.values());
        writeAll(merged);
        return entities;
    }


    public void delete(Patient entity) {
        if (entity == null) return;
        deleteByDni(entity.getDni());
    }

    public void deleteById(Integer id) {
        // si tu PK no es Integer, puedes no soportarlo o mapearlo.
        throw new UnsupportedOperationException("No soportado en CSV");
    }

    public void deleteAll() {
        writeAll(Collections.emptyList());
    }

    // ——— helpers ———
    private void writeAll(List<Patient> patients) {
        lock.writeLock().lock();
        File out = resolveOutputFile();
        out.getParentFile().mkdirs();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(out), charset());
             CSVPrinter printer = new CSVPrinter(writer, writeFormat())) {
            for (Patient p : patients) {
                print(printer, p);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void deleteByDni(String dni) {
        List<Patient> list = new ArrayList<>(findAll());
        list.removeIf(p -> Objects.equals(p.getDni(), dni));
        writeAll(list);
    }
}

