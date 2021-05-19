package io;

import logik.Puzzle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Konkrete implemetierung des Ausgabe Interfaces, fuer Dateiausgaben
 */
public class Dateiausgabe implements IAusgabe {
    private final String path;

    public Dateiausgabe(String path) {
        this.path = path;
        var dir = new File(path);
        if (dir.mkdir()) {
            System.out.println("Result Verzeichniss angelegt");
        }
    }

    @Override
    public void write(IOWrapper input) throws IOException {

        File file = new File(path + input.getName() + "-Ergebnis.txt");
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {

            String out = String.format("%s%s", input.getComment(), input.getPuzzle().toString());

            writer.write(out);
        }
    }
}
