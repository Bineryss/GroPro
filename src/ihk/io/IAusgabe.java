package ihk.io;

import ihk.logik.Puzzle;

import java.io.IOException;

public interface IAusgabe {
    public void write(Puzzle input, String comment) throws IOException;
}
