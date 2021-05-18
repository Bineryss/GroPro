package ihk.io;

import ihk.logik.Puzzle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class Dateiausgabe implements IAusgabe {
    private String path;

    public Dateiausgabe(String path) {
        this.path = path;
    }

    @Override
    public void write(Puzzle input, String comment) throws IOException {
        var dir = new File(path);
        if (dir.mkdir()) {
            System.out.println("Result Verzeichniss angelegt");
        }

        File file = new File(path + "-Ergebnis.txt");
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {

            String out = String.format("%s%s", comment, input.toString());

            writer.write(out);
        }
    }
}
