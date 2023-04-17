package cz.czechitas.lekce10.api;

import cz.czechitas.lekce10.engine.swing.MainWindow;
import cz.czechitas.lekce10.engine.swing.Utils;
import net.sevecek.util.ThreadUtils;
import net.sevecek.util.swing.JKeyboard;

/**
 * Mozek, který ovládá daného hráče pomocí klávesnice.
 */
public class KeyboardBrain implements Brain {

    private int keyCodeLeft;
    private int keyCodeUp;
    private int keyCodeRight;
    private int keyCodeDown;

    /**
     * Vytvoří mozek ovládaný pomocí standardních kláves – šipek.
     */
    public KeyboardBrain() {
        this(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT);
    }

    /**
     * Vytvoří mozek ovládaný pomocí alternativních kláves.
     * @param keyCodeUp
     * @param keyCodeLeft
     * @param keyCodeDown
     * @param keyCodeRight
     */
    public KeyboardBrain(KeyCode keyCodeUp, KeyCode keyCodeLeft, KeyCode keyCodeDown, KeyCode keyCodeRight) {
        this.keyCodeLeft = keyCodeLeft.getKeyEventVkCode();
        this.keyCodeUp = keyCodeUp.getKeyEventVkCode();
        this.keyCodeRight = keyCodeRight.getKeyEventVkCode();
        this.keyCodeDown = keyCodeDown.getKeyEventVkCode();
    }

    @Override
    public void controlPlayer(Player player) {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadUtils.sleep(20L);
            Utils.invokeAndWait(() -> {
                JKeyboard keyboard = MainWindow.getInstance().getKeyboard();
                if (keyboard.isKeyDown(keyCodeUp)) {
                    player.setOrientation(PlayerOrientation.UP);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeDown)) {
                    player.setOrientation(PlayerOrientation.DOWN);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeLeft)) {
                    player.setOrientation(PlayerOrientation.LEFT);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeRight)) {
                    player.setOrientation(PlayerOrientation.RIGHT);
                    player.moveForwardInternal();
                }
                player.getSprite().repaint();
            });
        }
    }

    public int getKeyCodeLeft() {
        return keyCodeLeft;
    }

    public int getKeyCodeUp() {
        return keyCodeUp;
    }

    public int getKeyCodeRight() {
        return keyCodeRight;
    }

    public int getKeyCodeDown() {
        return keyCodeDown;
    }
}
