package cz.czechitas.lekce10.api;

import java.awt.*;

/**
 * Kočka, která se snaží ulovit myš.
 */
public class Cat extends FourWayPlayer {

    public Cat(Point point) {
        super(point, PlayerType.BAD, "cat-left.png", "cat-down.png", "cat-up.png", "cat-right.png");
    }

    public Cat(int x, int y) {
        super(x, y, PlayerType.BAD, "cat-left.png", "cat-down.png", "cat-up.png", "cat-right.png");
    }

}
