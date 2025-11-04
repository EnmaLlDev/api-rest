package com.fp.api_rest.convertFiles.manager;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerFiles {

    public static List<String> readLines(String nombreFichero) throws IOException {
        List<String> lineas = new ArrayList<String>();
        try (FileReader fr = new FileReader(nombreFichero); BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {

                lineas.add(linea);
            }
        }
        return lineas;
    }

    public static void writeLines(String filePath, boolean append, List<String> lines) throws IOException {
        Path path = Paths.get(filePath).getParent();
        if (path != null && !Files.exists(path)) {
            Files.createDirectories(path);
        }
        try (FileWriter fw = new FileWriter(filePath, append); BufferedWriter bw = new BufferedWriter(fw)) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
    public static void printFileToConsole(String nombreFichero) throws IOException {
        List<String> lines = readLines(nombreFichero);
        for (String line: lines) {
            System.out.println(line);
        }
    }
    public List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
    public List<String> readLinesFromFile(String filename) throws IOException {
        return readAllLines(Paths.get(filename));
    }
    public void deleteIfExists(Path path) throws IOException {
        Files.deleteIfExists(path);
        Path parent = path.getParent();
        if (parent != null && Files.isDirectory(parent) && parent.getFileName().toString().startsWith("upload-")) {
            try {
                Files.deleteIfExists(parent);
            } catch (IOException ignored) {
            }
        }
    };
}