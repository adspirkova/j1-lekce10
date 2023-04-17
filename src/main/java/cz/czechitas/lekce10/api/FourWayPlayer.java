package cz.czechitas.lekce10.api;

import cz.czechitas.lekce10.engine.swing.Utils;

import javax.swing.*;
import java.awt.*;

public abstract class FourWayPlayer extends Player {

    private Icon leftImage;
    private Icon downImage;
    private Icon upImage;
    private Icon rightImage;

    protected FourWayPlayer(Point point, PlayerType type, String leftImage, String downImage, String upImage, String rightImage) {
        super(point, rightImage, type);
        loadImages(leftImage, downImage, upImage, rightImage);
    }

    protected FourWayPlayer(int x, int y, PlayerType type, String leftImage, String downImage, String upImage, String rightImage) {
        super(x, y, rightImage, type);
        loadImages(leftImage, downImage, upImage, rightImage);
    }

    private void loadImages(String leftImage, String downImage, String upImage, String rightImage) {
        Utils.invokeAndWait(() -> {
            this.leftImage = Utils.loadSprite(leftImage);
            this.downImage = Utils.loadSprite(downImage);
            this.upImage = Utils.loadSprite(upImage);
            this.rightImage = Utils.loadSprite(rightImage);

        });
    }

    @Override
    protected void setOrientation(PlayerOrientation orientation) {
        Utils.invokeAndWait(() -> {
            super.setOrientation(orientation);
            switch (orientation) {
                case UP:
                    getSprite().setIcon(upImage);
                    break;
                case DOWN:
                    getSprite().setIcon(downImage);
                    break;
                case LEFT:
                    getSprite().setIcon(leftImage);
                    break;
                case RIGHT:
                    getSprite().setIcon(rightImage);
                    break;
            }
        });
    }

}
