package ihk;

import ihk.io.Dateiausgabe;
import ihk.io.Dateieingabe;
import ihk.io.IOWrapper;
import ihk.logik.Puzzle;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        IOWrapper wrapper;
        String path = "C:\\Users\\sebas\\Documents\\GroPro-Otto\\Code\\resources\\Beispiel 5.txt";
        try {
            wrapper = new Dateieingabe(path).read();
            Puzzle p = wrapper.getPuzzle();
            long start = System.currentTimeMillis();

            p.calc();
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            System.out.println("Elapsed Time in Milli: " + elapsedTimeMillis / 1000F);
            new Dateiausgabe(path).write(p, wrapper.getComment());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
