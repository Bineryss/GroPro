package ihk.io;

import ihk.logik.Puzzle;
import ihk.logik.Wuerfel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dateieingabe implements IEingabe {
    private final String path;

    public Dateieingabe(String path) {
        this.path = path;
    }

    @Override
    public IOWrapper read() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(path))) {
            String comment = "";
            List<Wuerfel> wuerfel = new ArrayList<>();
            int[] dimension = new int[3];
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    line = scanner.nextLine();
                }
                if (line.startsWith("//")) {
                    comment += line.substring(2) + "\n";
                } else if (line.startsWith("Dimension")) {
                    String[] dimString = line.split(" ");
                    String[] dimNumber = dimString[1].split(",");
                    dimension = convertStringToInt(dimNumber);
                } else {
                    wuerfel.add(convertStringToWuerfel(line));
                }
            }
            Puzzle puzzle = new Puzzle(wuerfel, dimension[0], dimension[1], dimension[2]);
            return new IOWrapper(comment, puzzle);
        }
    }

    private Wuerfel convertStringToWuerfel(String line) {
        String[] nameNumber = line.split(":");
        nameNumber[1] = nameNumber[1].trim();
        String[] numbers = nameNumber[1].split(" ");
        return new Wuerfel(nameNumber[0], convertStringToInt(numbers));
    }

    private int[] convertStringToInt(String[] input) {
        int[] out = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            out[i] = Integer.parseInt(input[i]);
        }
        return out;
    }

}
