package cz.czechitas.lekce10.api;

import cz.czechitas.lekce10.engine.swing.MainWindow;
import cz.czechitas.lekce10.engine.swing.Utils;

import java.awt.*;

/**
 * This class is just a object oriented wrapped for the Gameplay singleton
 * to avoid explanation of factory methods (<code>Gameplay.getInstance()</code>) to students.
 * You can instantiate as many GameManagers as you want
 * and they all will delegate its methods to the Gameplay singleton.
 *
 * Usage:
 * GameManager manager;
 * manager = new GameManager();
 * manager.getSize();
 */
public class GameManager {

    public int getWidth() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getWidth();
        });
    }

    public int getHeight() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getHeight();
        });
    }

    public Dimension getSize() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getSize();
        });
    }

}
