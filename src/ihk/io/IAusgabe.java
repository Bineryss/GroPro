package ihk.io;

import ihk.logik.Puzzle;

import java.io.IOException;

/**
 * Intfercae fuer die Ausgabe
 */
public interface IAusgabe {

    /**
     * Schreibt das Puzzel als Ausgabe
     *
     * @param input   Das Puzel, dass ausgegebenen werden soll
     * @param comment Der Kommentar, der dazu muss
     * @throws IOException Falls es ein Problem gibt, wird die IOException geworfen
     */
    void write(Puzzle input, String comment) throws IOException;
}
