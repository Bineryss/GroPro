package ihk;

import ihk.io.Dateieingabe;
import ihk.io.IOWrapper;
import ihk.logik.Puzzle;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        IOWrapper wrapper = null;
        try {
            wrapper = new Dateieingabe("C:\\Users\\sebas\\Documents\\GroPro-Otto\\Code\\resources\\Beispiel 1.txt").read();
            Puzzle p = wrapper.getPuzzle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
