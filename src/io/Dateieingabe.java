package io;

import logik.Puzzle;
import logik.Cube;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Konkrete implemetierung des Eingabe Interfaces, fuer Dateieingaben
 */
public class Dateieingabe implements IEingabe {
    private final String path;
    private final String name;

    public Dateieingabe(String path) {
        this.path = path;
        String[] spl = path.split("\\\\");
        this.name = spl[spl.length - 1];
    }

    @Override
    public IOWrapper read() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(path))) {
            String comment = "";
            List<Cube> cube = new ArrayList<>();
            int[] dimension = new int[3];
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    line = scanner.nextLine();
                }
                if (line.startsWith("//")) {
                    comment += line + "\n";
                } else if (line.startsWith("Dimension")) {
                    String[] dimString = line.split(" ");
                    String[] dimNumber = dimString[1].split(",");
                    dimension = convertStringToInt(dimNumber);
                } else {
                    cube.add(convertStringToCubes(line));
                }
            }
            Puzzle puzzle = new Puzzle(cube, dimension[0], dimension[1], dimension[2]);
            return new IOWrapper(comment, puzzle, name);
        }
    }

    private Cube convertStringToCubes(String line) {
        String[] nameNumber = line.split(":");
        nameNumber[1] = nameNumber[1].trim();
        String[] numbers = nameNumber[1].split(" ");
        return new Cube(nameNumber[0], convertStringToInt(numbers));
    }

    private int[] convertStringToInt(String[] input) {
        int[] out = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            out[i] = Integer.parseInt(input[i]);
        }
        return out;
    }

}
