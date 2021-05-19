package ihk.io;

import java.io.FileNotFoundException;

/**
 * Interface fuer die Ausgabe
 */
public interface IEingabe {
    /**
     * Liest ein Puzzle als eingabe ein
     *
     * @return Einen IOWrapper, der sowohl ein Puzzle und den Kommentar enthaelt
     * @throws FileNotFoundException Falls beim einlesen ein fehler ensteht
     */
    IOWrapper read() throws FileNotFoundException;
}
