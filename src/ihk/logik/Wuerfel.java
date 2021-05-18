package ihk.logik;

import java.util.Arrays;

public class Wuerfel {
    private final String name;
    private final int[] seiten;

    public Wuerfel(String name, int[] seiten) {
        this.name = name;
        this.seiten = seiten;
    }

    public void drehen(Axen axe) {
        switch (axe) {
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

    public String getName() {
        return name;
    }

    public int[] getSeiten() {
        return seiten;
    }

    public int countSeiten() {
        int sum = 0;
        for (var el : seiten) {
            if (el != 0) {
                sum++;
            }
        }
        return sum;
    }

    private void rotateX() {
        //Rotate off Axis
        int tmp = seiten[0];
        seiten[0] = minusOne(seiten[3]);
        seiten[3] = minusOne(seiten[5]);
        seiten[5] = minusOne(seiten[1]);
        seiten[1] = minusOne(tmp);

        //Rotate on Axis
        seiten[2] = minusOne(seiten[2]);
        seiten[4] = plusOne(seiten[4]);
    }

    private void rotateY() {
        //Rotate off Axis
        int tmp = seiten[2];
        seiten[2] = seiten[0];

        int p4 = seiten[4];
        p4 = plusOne(p4);
        p4 = plusOne(p4);
        seiten[0] = p4;

        int p5 = seiten[5];
        p5 = plusOne(p5);
        p5 = plusOne(p5);
        seiten[4] = p5;

        seiten[5] = tmp;


        //Rotate on Axis
        seiten[3] = minusOne(seiten[3]);
        seiten[1] = plusOne(seiten[1]);

    }

    private void rotateZ() {
        //Rotate off Axis
        int tmp = seiten[1];
        seiten[1] = seiten[2];
        seiten[2] = seiten[3];
        seiten[3] = seiten[4];
        seiten[4] = tmp;

        //Rotate on Axis
        seiten[0] = plusOne(seiten[0]);
        seiten[5] = minusOne(seiten[5]);
    }

    private int plusOne(int seite) {
        if (seite == 0) {
            return 0;
        }
        if (seite == 4) {
            return 1;
        } else {
            return ++seite;
        }
    }

    private int minusOne(int seite) {
        if (seite == 0) {
            return 0;
        }
        if (seite == 1) {
            return 4;
        } else {
            return --seite;
        }
    }

    public String toString() {
        return String.format("%s: %s", name, Arrays.toString(seiten));
    }
}
