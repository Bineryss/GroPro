package ihk.logik;

import java.util.List;

public class Puzzle {
    private List<Wuerfel> wuerfel;
    private Wuerfel[][][] dimension;
    private boolean istLoesbar;

    public Puzzle(List<Wuerfel> wuerfel, int dimX, int dimY, int dimZ) {
        this.wuerfel = wuerfel;
        this.dimension = new Wuerfel[dimX][dimY][dimZ];
    }

    public void calc() {

    }

    private List<Wuerfel[]> schneideWuerfel() {
        return null;
    }

    private boolean pruefeNachbarn(int x, int y, int z) {
        return false;
    }

    private boolean pruefeObDimPasst() {
        return false;
    }

    private boolean pruefeObWuerefelFormPassen() {
        return false;
    }

    public Wuerfel[][][] getDimension() {
        return dimension;
    }

    public boolean isIstLoesbar() {
        return istLoesbar;
    }
}
