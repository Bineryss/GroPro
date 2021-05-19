package io;

import logik.Puzzle;

import java.io.IOException;

/**
 * Intfercae fuer die Ausgabe
 */
public interface IAusgabe {

    /**
     * Schreibt das Puzzel als Ausgabe
     *
     * @param input Das Puzzel, dass ausgegebenen werden soll, mit Komentar und Name der Datei
     * @throws IOException Falls es ein Problem gibt, wird die IOException geworfen
     */
    void write(IOWrapper input) throws IOException;
}
