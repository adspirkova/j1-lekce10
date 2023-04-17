package cz.czechitas.lekce10.api;

import java.awt.*;

/**
 * Myš, která se snaží sežrat veškeré dostupné jídlo a nestat se sama potravou pro kočku.
 */
public class Mouse extends FourWayPlayer {

    public Mouse(Point point) {
        super(point, PlayerType.GOOD, "mouse-left.png", "mouse-down.png", "mouse-up.png", "mouse-right.png");
    }

    public Mouse(int x, int y) {
        super(x, y, PlayerType.GOOD, "mouse-left.png", "mouse-down.png", "mouse-up.png", "mouse-right.png");
    }
}
