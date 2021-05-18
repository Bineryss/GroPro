package ihk.logik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Puzzle {
    private final List<Cube> cube;
    private Cube[][][] dimension;
    private final int[][][] form;
    private boolean hasResult;


    public Puzzle(List<Cube> cube, int dimX, int dimY, int dimZ) {
        this.cube = cube;
        this.dimension = new Cube[dimX][dimY][dimZ];
        this.form = new int[dimX][dimY][dimZ];

    }

    public void calc() {
        if (!hasEnoughCubes()) {
            hasResult = false;
            return;
        }
        calcForm();
        List<Cube>[] sortedCubes = sortCubes();

        if (!hasCorrectCubes()) {
            hasResult = false;
            return;
        }
        dimension = calculatePossibleResult(new Cube[dimension.length][dimension[0].length][dimension[0][0].length], 0, 0, 0, sortedCubes);
        System.out.println("Result: ");
        if (dimension != null) {
            System.out.println(convertToPrint(dimension));
        } else {
            System.out.println("Not solvable");
        }

    }

    private Cube[][][] calculatePossibleResult(Cube[][][] lastField, int xPos, int yPos, int zPos, List<Cube>[] unusedCubes) {
        //Holt sich alle Wuerfel, die auf die aktuelle Position passen.
        List<Cube> currentW = unusedCubes[form[xPos][yPos][zPos]];

        Cube[][][] currentField = deepCopyField(lastField);

        //Iteriert alle passenden Wuerfel durch
        for (var el : currentW) {
            //erzeugt neue moeglichkeiten Liste, ohne das aktuelle objekt
            List<Cube>[] newAvailableCubes = copyAndRemove(unusedCubes, el);

            //Probiert alle rotationen durch
            //Reduktion von 4 auf 3 hat laufzeit enorm verbessert
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        //Prueft ob der Wuerfel passt und fuegt ihn ein
                        if (pruefeNachbarnUndFuegeEin(xPos, yPos, zPos, el, currentField)) {
                            //Geht and die naechste Position und wiederholt
                            if (xPos + 1 < dimension.length) {
                                currentField = calculatePossibleResult(currentField,
                                        xPos + 1, yPos, zPos, newAvailableCubes);
                            } else if (yPos + 1 < dimension[xPos].length) {
                                currentField = calculatePossibleResult(currentField,
                                        0, yPos + 1, zPos, newAvailableCubes);
                            } else if (zPos + 1 < dimension[xPos][yPos].length) {
                                currentField = calculatePossibleResult(currentField,
                                        0, 0, zPos + 1, newAvailableCubes);
                            }
                            //Bedingung dafuer, dass jeder Wuerfel gesetzt wurde
                            else {
                                hasResult = true;
                            }

                            if (hasResult) {
                                return currentField;
                            }
                        }
                        //Rotation auf Z 90
                        el.rotate(Axis.Z);
                    }
                    //Rotation auf Y 90
                    el.rotate(Axis.Y);
                }
                //Rotation auf X 90
                el.rotate(Axis.X);
            }
        }
        return lastField;
    }

    private Cube[][][] deepCopyField(Cube[][][] copy) {
        Cube[][][] currentField = new Cube[copy.length][copy[0].length][copy[0][0].length];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                currentField[i][j] = Arrays.copyOf(copy[i][j], copy[i][j].length);
            }
        }
        return currentField;
    }

    private List<Cube>[] copyAndRemove(List<Cube>[] list, Cube item) {
        int listPos = item.countNonFlatFaces();
        List<Cube> ncW = new ArrayList<>(list[listPos]);
        ncW.remove(item);

        List<Cube>[] nWuerfel = Arrays.copyOf(list, list.length);
        nWuerfel[listPos] = ncW;
        return nWuerfel;
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

    private List<Cube>[] sortCubes() {
        ArrayList<Cube>[] wuerfelSortiert = new ArrayList[6];
        for (int i = 0; i < wuerfelSortiert.length; i++) {
            wuerfelSortiert[i] = new ArrayList<>();
        }

        for (var el : cube) {
            wuerfelSortiert[el.countNonFlatFaces()].add(el);
        }

        return wuerfelSortiert;
    }

    //Prueft ob passt, wenn ja fuegt wuerfel ein

    /**
     * Prueft ob der übergebene Wuerfel an der aktuellen Stelle passt.
     * D.h. es stehen keine Dreieck nach außen und alle anliegenden Wuerfel haben passende Dreiecke.
     * @param x Position an X - Achse
     * @param y Position an Y - Achse
     * @param z Position an Y - Achse
     * @param w Der Wuerfel, der eingefuegt werden soll
     * @param form Der Zustand des akuellen Loesungsverchus
     * @return Gibt an, ob der Wuerfel an der Angegebenen Stelle stehen kann
     */
    private boolean pruefeNachbarnUndFuegeEin(int x, int y, int z, Cube w, Cube[][][] form) {
        int[] seiten = w.getFace();
        //Prueft ob randwuerfel keine dreiecke haben
        if (x == 0 && seiten[4] != 0 || x == form.length - 1 && seiten[2] != 0) {
            return false;
        }
        if (y == 0 && w.getFace()[1] != 0 || y == form[x].length - 1 && seiten[3] != 0) {
            return false;
        }
        if (z == 0 && w.getFace()[5] != 0 || z == form[x][y].length - 1 && seiten[0] != 0) {
            return false;
        }

        //Pruefe x-
        if (x > 0) {
            Cube nX = form[x - 1][y][z];
            int seiteX = nX.getFace()[2];
            if (!matchingFaces(w.getFace()[4], seiteX, Axis.X)) {
                return false;
            }
        }
        //Pruefe y-
        if (y > 0) {
            Cube nY = form[x][y - 1][z];
            int seiteY = nY.getFace()[3];
            if (!matchingFaces(w.getFace()[1], seiteY, Axis.Y)) {
                return false;
            }
        }
        //Pruefe z-
        if (z > 0) {
            Cube nY = form[x][y][z - 1];
            int seiteY = nY.getFace()[0];
            if (!matchingFaces(w.getFace()[5], seiteY, Axis.Z)) {
                return false;
            }
        }
        form[x][y][z] = w;
        return true;
    }


    /**
     * Es wird verglichen ob die beiden angebenen Seiten aufeinander passen.
     * Auf der X und Y - Achse, passen die Seiten 1-2 und 3-4 aufeinander.
     * Fuer die Z - Achse passen die Seiten 1-4 und 2-3 aufeinander.
     *
     * @param a Seite des ersten Wuerfels
     * @param b Seite des zweiten Wuerfels
     * @param axe Achse, auf der Verglichen wird
     * @return Gibt an ob die zwei angebenen Seiten aufeinander passen
     */
    private boolean matchingFaces(int a, int b, Axis axe) {
        if (axe == Axis.Z) {
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

    private boolean hasEnoughCubes() {
        return cube.size() == dimension.length * dimension[0].length * dimension[0][0].length;
    }

    //Todo implementieren, dazu form durchgehen und schauen, dass genug Wuerfel da sind
    private boolean hasCorrectCubes() {
        return true;
    }

    public Cube[][][] getDimension() {
        return dimension;
    }

    /**
     *
     * @return Gibt als String das Puzzel in Ausgabeformat an
     */
    public String toString() {
        if (hasResult) {
            return String.format("Dimension %s,%s,%s\n%s", dimension.length,
                    dimension[0].length, dimension[0][0].length, convertToPrint(dimension));
        } else {
            return "Es gibt kein Ergebniss";
        }
    }

    private String convertToPrint(Cube[][][] input) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                for (int k = 0; k < input[i][j].length; k++) {
                    out.append("[")
                            .append(i + 1)
                            .append(",")
                            .append(j + 1)
                            .append(",")
                            .append(k + 1)
                            .append("] ")
                            .append(input[i][j][k]);

                    if (i + 1 < input.length || j + 1 < input[i].length || k + 1 < input[i][j].length) {
                        out.append("\n");
                    }
                }
            }
        }
        return out.toString();
    }
}