package com.fp.api_rest.convertFiles.manager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class managerFiles {

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
}