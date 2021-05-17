package ihk.logik;

public class Wuerfel {
    private final String name;
    private int[] seiten;

    public Wuerfel(String name, int[] seiten) {
        this.name = name;
        this.seiten = seiten;
    }

    public void drehen(char axis, int amount) {
        //Dreht den würfel passend
    }

    public String getName() {
        return name;
    }

    public int[] getSeiten() {
        return seiten;
    }

}
