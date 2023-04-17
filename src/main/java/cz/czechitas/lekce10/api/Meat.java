package cz.czechitas.lekce10.api;

import java.awt.*;

/**
 * Maso, potrava pro myš.
 */
public class Meat extends Player {

    public Meat(Point point) {
        super(point, "meat.png", PlayerType.FOOD);
    }

    public Meat(int x, int y) {
        super(x, y, "meat.png", PlayerType.FOOD);
    }

}
