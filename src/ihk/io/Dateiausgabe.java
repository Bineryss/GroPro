package ihk.io;

import ihk.logik.Puzzle;

public class Dateiausgabe implements IAusgabe {
    private String path;

    public Dateiausgabe(String path) {
        this.path = path;
    }

    @Override
    public void write(Puzzle input, String comment) {

    }
}
