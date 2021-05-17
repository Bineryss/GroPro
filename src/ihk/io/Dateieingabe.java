package ihk.io;

public class Dateieingabe implements IEingabe {
    private String path;

    @Override
    public IOWrapper read() {
        return null;
    }

    public Dateieingabe(String path) {
        this.path = path;
    }
}
