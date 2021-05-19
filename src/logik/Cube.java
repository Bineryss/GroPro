package logik;

/**
 * Representiert eine Wuerfel aus dem Holzspiel.
 */
public class Cube {
    private final String name;
    private final int[] face;

    public Cube(String name, int[] face) {
        this.name = name;
        this.face = face;
    }

    /**
     * Rotiert den Wuerfel um 90 Grad um die angegebene Achse.
     * Beachtet dabei auch, dass die Dreiecke abhaengig von der Rotation gauch rotiert werden muessen.
     *
     * @param axis Die Achse um die Rotiert werden soll
     */
    public void rotate(Axis axis) {
        switch (axis) {
            case X -> {
                rotateX();
            }
            case Y -> {
                rotateY();
            }
            case Z -> {
                rotateZ();
            }
        }
    }

    /**
     *  Das Array representiert die einzelnen Seiten des Wuerfels als Risszeichnung (vgl. Dokumentation).
     *
     *  Dabei steht 0 fuer eine Flache Seite und die Zahlen 1 - 4 fuer die verscheidenen Dreiecke.
     * @return
     */
    public int[] getFace() {
        return face;
    }

    /**
     * Diese Methode dient dazu, auszulesen, wie viele Dreiecke ein Wuerfel besitzt.
     *
     * @return anzahl an Seiten mit einem Dreieck
     */
    public int countNonFlatFaces() {
        int sum = 0;
        for (var el : face) {
            if (el != 0) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Bei Rotation um die X-Achse werden die Flaechen 3 und 5 nur gedreht.
     * Die Flachen 1,2,4,6 werden miteinander getauscht, so dass eine 90 Graddrehung um die X-Achse entsteht.
     *
     * Dabei muss ebenfalls drauf geachtet werden, dass die Dreiecke an den Feldern 1,2,4,6, auch gedreht werden muessen.
     */
    private void rotateX() {
        //Rotate off Axis
        int tmp = face[0];
        face[0] = minusOne(face[3]);
        face[3] = minusOne(face[5]);
        face[5] = minusOne(face[1]);
        face[1] = minusOne(tmp);

        //Rotate on Axis
        face[2] = minusOne(face[2]);
        face[4] = plusOne(face[4]);
    }

    private void rotateY() {
        //Rotate off Axis
        int tmp = face[2];
        face[2] = face[0];

        int p4 = face[4];
        p4 = plusOne(p4);
        p4 = plusOne(p4);
        face[0] = p4;

        int p5 = face[5];
        p5 = plusOne(p5);
        p5 = plusOne(p5);
        face[4] = p5;

        face[5] = tmp;


        //Rotate on Axis
        face[3] = minusOne(face[3]);
        face[1] = plusOne(face[1]);

    }

    private void rotateZ() {
        //Rotate off Axis
        int tmp = face[1];
        face[1] = face[2];
        face[2] = face[3];
        face[3] = face[4];
        face[4] = tmp;

        //Rotate on Axis
        face[0] = plusOne(face[0]);
        face[5] = minusOne(face[5]);
    }

    private int plusOne(int face) {
        if (face == 0) {
            return 0;
        }
        if (face == 4) {
            return 1;
        } else {
            return ++face;
        }
    }

    private int minusOne(int face) {
        if (face == 0) {
            return 0;
        }
        if (face == 1) {
            return 4;
        } else {
            return --face;
        }
    }

    public String toString() {
        StringBuilder tries = new StringBuilder();
        for (var el : face) {
            tries.append(el).append(" ");
        }
        String out = tries.toString().trim();

        return String.format("%s: %s", name, out);
    }
}
