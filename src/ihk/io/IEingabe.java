package ihk.io;

import java.io.FileNotFoundException;

public interface IEingabe {
    public IOWrapper read() throws FileNotFoundException;
}
