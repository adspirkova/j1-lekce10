package cz.czechitas.lekce10.api;

import cz.czechitas.lekce10.engine.Gameplay;
import cz.czechitas.lekce10.engine.swing.MainWindow;
import cz.czechitas.lekce10.engine.swing.Utils;
import net.sevecek.util.ApplicationPublicException;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static cz.czechitas.lekce10.api.CollisionType.NO_COLLISION;
import static cz.czechitas.lekce10.api.CollisionType.STACKABLE_COLLISION;

public abstract class Figure {

    private JLabel sprite;
    private volatile boolean isAlive = true;

    protected Figure(Point point, String imageName) {
        this(point.x, point.y, imageName);
    }

    protected Figure(int x, int y, String imageName) {
        Utils.invokeAndWait(() -> {
            Icon picture = Utils.loadSprite(imageName);
            sprite = new JLabel(picture);
            sprite.setLocation(x, y);
            sprite.setSize(picture.getIconWidth(), picture.getIconHeight());
            Container mainWindowContentPane = MainWindow.getInstance().getGamepad();
            mainWindowContentPane.add(sprite, "external");

            moveElsewhereIfColliding();
            mainWindowContentPane.repaint();
        });
    }

    private void moveElsewhereIfColliding() {
        Container mainWindowContentPane = MainWindow.getInstance().getGamepad();
        Gameplay gameplay = Gameplay.getInstance();
        int x = sprite.getX();
        int y = sprite.getY();
        int originalX = x;
        int originalY = y;
        while (true) {
            CollisionType collisionResult = gameplay.detectCollisionWithAnyOtherFigure(this);
            if (collisionResult == NO_COLLISION) {
                return;
            } else if (collisionResult == STACKABLE_COLLISION) {
                replaceWithStackedImage();
                return;
            } else {
                x = ((x / 50) * 50) + 50;
                if (x + sprite.getWidth() > mainWindowContentPane.getWidth()) {
                    x = originalX;
                    sprite.setLocation(x, y);
                    break;
                }
                sprite.setLocation(x, y);
            }
        }
        while (true) {
            CollisionType collisionResult = gameplay.detectCollisionWithAnyOtherFigure(this);
            if (collisionResult == NO_COLLISION) {
                return;
            } else if (collisionResult == STACKABLE_COLLISION) {
                replaceWithStackedImage();
                return;
            } else {
                y = ((y / 50) * 50) + 50;
                if (y + sprite.getHeight() > mainWindowContentPane.getHeight()) {
                    y = originalY;
                    sprite.setLocation(x, y);
                    break;
                }
                sprite.setLocation(x, y);
            }
        }

        Random randomGenerator = new Random();
        int attemptCount = 0;
        while (true) {
            x = (randomGenerator.nextInt(mainWindowContentPane.getWidth() - sprite.getWidth()) / 50) * 50;
            y = (randomGenerator.nextInt(mainWindowContentPane.getHeight() - sprite.getHeight()) / 50) * 50;
            sprite.setLocation(x, y);
            CollisionType collisionResult = gameplay.detectCollisionWithAnyOtherFigure(this);
            if (collisionResult == NO_COLLISION) {
                return;
            } else if (collisionResult == STACKABLE_COLLISION) {
                replaceWithStackedImage();
                return;
            }
            attemptCount++;
            if (attemptCount > 1000) {
                throw new ApplicationPublicException("We were unable to find a place for the figure even after many attempts. Is the gameboard full?");
            }
        }
    }

    private void replaceWithStackedImage() {
        sprite.getParent().setComponentZOrder(sprite, 1);
        sprite.setIcon(((Stackable) this).getStackableIcon());
    }

    public JLabel getSprite() {
        return sprite;
    }

    protected void repaint() {
        sprite.repaint();
    }

    public int getX() {
        return Utils.invokeAndWait(() -> {
            int x = sprite.getX();
            return x;
        });
    }

    public int getY() {
        return Utils.invokeAndWait(() -> {
            int y = sprite.getY();
            return y;
        });
    }

    public int getWidth() {
        return Utils.invokeAndWait(() -> {
            int width = sprite.getWidth();
            return width;
        });
    }

    public int getHeight() {
        return Utils.invokeAndWait(() -> {
            int height = sprite.getHeight();
            return height;
        });
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setLocation(Point point) {
        Utils.invokeAndWait(() -> sprite.setLocation(point));
    }

    public Point getLocation() {
        return Utils.invokeAndWait(() -> sprite.getLocation());
    }

    public void remove() {
        Utils.invokeAndWait(() -> {
            isAlive = false;
            Container contentPane = MainWindow.getInstance().getGamepad();
            sprite.setVisible(false);
            contentPane.repaint();
        });
    }

}
