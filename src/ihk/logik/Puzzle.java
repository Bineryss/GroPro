package ihk.logik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Puzzle {
    private List<Wuerfel> wuerfel;
    private Wuerfel[][][] dimension;
    private int[][][] form;
    private boolean istLoesbar;


    public Puzzle(List<Wuerfel> wuerfel, int dimX, int dimY, int dimZ) {
        this.wuerfel = wuerfel;
        this.dimension = new Wuerfel[dimX][dimY][dimZ];
        this.form = new int[dimX][dimY][dimZ];

    }

    public void calc() {
        if (!pruefeObDimPasst()) {
            istLoesbar = false;
            return;
        }
        calcForm();
        List<Wuerfel>[] sortedWuerfel = schneideWuerfel();

        if (!pruefeObWuerfelFormPassen()) {
            istLoesbar = false;
            return;
        }
        dimension = calcR(new Wuerfel[dimension.length][dimension[0].length][dimension[0][0].length], 0, 0, 0, sortedWuerfel);
        System.out.println("Ergebnis:");
        System.out.println(convertToPrint(dimension));

    }

    private Wuerfel[][][] calcR(Wuerfel[][][] start, int xPos, int yPos, int zPos, List<Wuerfel>[] wuerfel) {

        //Holt sich alle Wuerfel, die auf die aktuelle Position passen.
        int currentForm = form[xPos][yPos][zPos];
        List<Wuerfel> currentW = wuerfel[currentForm];

        Wuerfel[][][] currentResult = start;

        //Iteriert alle passenden Wuerfel durch
        for (var el : currentW) {

            //erzeugt neue moeglichkeiten Liste, ohen das aktuelle objekt
            List<Wuerfel> ncW = new ArrayList<>(currentW);
            ncW.remove(el);

            List<Wuerfel>[] nWuerfel = Arrays.copyOf(wuerfel, wuerfel.length);
            nWuerfel[currentForm] = ncW;

            //Probiert alle rotationen durch -> ?schlau schauen, was überhaupt geddreht werden muss?
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        //Prueft ob der Wuerfel passt und fuegt ihn ein
                        if (pruefeNachbarnUndFuegeEin(xPos, yPos, zPos, el, currentResult)) {

                            //Geht and die naechste Position und wiederholt
                            if (xPos + 1 < dimension.length) {
                                xPos++;
                            } else if (yPos + 1 < dimension[xPos].length) {
                                xPos = 0;
                                yPos++;
                            } else if (zPos + 1 < dimension[xPos][yPos].length) {
                                xPos = 0;
                                yPos = 0;
                                zPos++;
                            }
                            return calcR(currentResult, xPos, yPos, zPos, nWuerfel);
                        }

                        //Rotation auf Z 90
                        el.drehen(Axen.Z);
                    }
                    //Rotation auf Y 90
                    el.drehen(Axen.Y);
                }
                //Rotation auf X 90
                el.drehen(Axen.X);
            }
        }

        System.out.println("**********************");
        System.out.printf("%s, %s, %s \n", xPos, yPos, zPos);
        System.out.println(convertToPrint(currentResult));

        return currentResult;
    }

    private void calcForm() {
        for (int i = 0; i < dimension.length; i++) {
            for (int j = 0; j < dimension[i].length; j++) {
                for (int k = 0; k < dimension[i][j].length; k++) {
                    int sumForm = 0;
                    if (k > 0) {
                        sumForm++;
                    }
                    if (k + 1 < dimension[i][j].length) {
                        sumForm++;
                    }

                    if (j > 0) {
                        sumForm++;
                    }
                    if (j + 1 < dimension[i].length) {
                        sumForm++;
                    }

                    if (i > 0) {
                        sumForm++;
                    }
                    if (i + 1 < dimension.length) {
                        sumForm++;
                    }
                    form[i][j][k] = sumForm;
                }
            }
        }
    }

    private List<Wuerfel>[] schneideWuerfel() {
        ArrayList<Wuerfel>[] wuerfelSortiert = new ArrayList[6];
        for (int i = 0; i < wuerfelSortiert.length; i++) {
            wuerfelSortiert[i] = new ArrayList<>();
        }

        for (var el : wuerfel) {
            wuerfelSortiert[el.countSeiten()].add(el);
        }

        return wuerfelSortiert;
    }

    //Prueft ob passt, wenn ja fuegt wuerfel ein
    private boolean pruefeNachbarnUndFuegeEin(int x, int y, int z, Wuerfel w, Wuerfel[][][] form) {
        int[] seiten = w.getSeiten();
        //Prueft ob randwuerfel keine dreiecke haben
        if (x == 0 && seiten[4] != 0 || x == form.length - 1 && seiten[2] != 0) {
            return false;
        }

        if (y == 0 && w.getSeiten()[1] != 0 || y == form[x].length - 1 && seiten[3] != 0) {
            return false;
        }

        if (z == 0 && w.getSeiten()[5] != 0 || y == form[x][y].length - 1 && seiten[0] != 0) {
            return false;
        }

        //Pruefe x-
        if (x > 0) {
            Wuerfel nX = form[x - 1][y][z];
            int seiteX = nX.getSeiten()[2];
            if (!matchingFaces(w.getSeiten()[4], seiteX, Axen.X)) {
                return false;
            }
        }
        //Pruefe x+ -> muss das gemacht werden? -> ne
//        if (x < form.length - 1) {
//            Wuerfel pX = form[x + 1][y][z];
//            int seiteX = pX.getSeiten()[4];
//            if (!matchingFaces(w.getSeiten()[2], seiteX)) {
//                return false;
//            }
//        }
        if (y > 0) {
            Wuerfel nY = form[x][y - 1][z];
            int seiteY = nY.getSeiten()[3];
            if (!matchingFaces(w.getSeiten()[1], seiteY, Axen.Y)) {
                return false;
            }
        }

        if (z > 0) {
            Wuerfel nY = form[x][y][z - 1];
            int seiteY = nY.getSeiten()[0];
            if (!matchingFaces(w.getSeiten()[5], seiteY, Axen.Z)) {
                return false;
            }
        }

        form[x][y][z] = w;
        return true;
    }


    //Matching faces Y: 1-2; 3-4
    //Matching faces X: 1-2; 3-4
    //Matching faces Z: 1-4; 2-3
    private boolean matchingFaces(int a, int b, Axen axe) {
        if (axe == Axen.Z) {
            if (a == 1 && b == 4 || b == 1 && a == 4) {
                return true;
            }
            return a == 2 && b == 3 || a == 3 && b == 2;
        }
        if (a == 1 && b == 2 || b == 1 && a == 2) {
            return true;
        }
        return a == 3 && b == 4 || a == 4 && b == 3;
    }

    private boolean pruefeObDimPasst() {
        return wuerfel.size() == dimension.length * dimension[0].length * dimension[0][0].length;
    }

    private boolean pruefeObWuerfelFormPassen() {
        return true;
    }

    public Wuerfel[][][] getDimension() {
        return dimension;
    }


    public String toString() {
        if (istLoesbar) {
            //TODO: Ausgabe hinzufuegen
            return "String Magie";
        } else {
            return "Es gibt kein Ergebniss";
        }
    }

    private String convertToPrint(Wuerfel[][][] input) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                for (int k = 0; k < input[i][j].length; k++) {
                    out.append("[")
                            .append(i)
                            .append(",")
                            .append(j)
                            .append(",")
                            .append(k)
                            .append("] ")
                            .append(input[i][j][k])
                            .append("\n");
                }
            }
        }
        return out.toString();
    }
}
