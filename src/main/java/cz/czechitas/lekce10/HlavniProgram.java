package cz.czechitas.lekce10;

import cz.czechitas.lekce10.api.*;
import cz.czechitas.lekce10.engine.Gameplay;
import cz.czechitas.lekce10.engine.swing.MainWindow;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Hlaví třída pro hru Kočka–myš–sýr.
 */
public class HlavniProgram {
    private Cat tom;
    private Mouse jerry;

    private Gameplay gameplay = Gameplay.getInstance();
    private Uloziste uloziste = new Uloziste();

    /**
     * Spouštěcí metoda celé aplikace.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new HlavniProgram().run();
    }

    /**
     * Hlavní metoda obsahující výkonný kód.
     */
    public void run() throws IOException {
        MainWindow.getInstance().getUlozitStavButton().addActionListener(this::handleUlozitAction);
        MainWindow.getInstance().getNacistStavButton().addActionListener(this::handleNacistAction);

        uloziste.nacistPlochuZeSouboru();

        tom = uloziste.getCat();
        //tom.setBrain(new KeyboardBrain(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D));

        jerry = uloziste.getMouse();
        jerry.setBrain(new KeyboardBrain());

        chytMys();
    }

    public void chytMys() {
        while (gameplay.isGameRunning()) {
            jdiZaJerrymVyhybejSeStromum();
        }
    }

    private void jdiZaJerrymVyhybejSeStromum() {
        int horizontalniRozdil = tom.getX() - jerry.getX(); // záporný = Tom je vlevo od Jerryho, kladný = Tom je vpravo od Jerryho
        if (horizontalniRozdil < 0) {   //Tom je vlevo od Jerryho
            otocSeVpravo();
            while (gameplay.isGameRunning() && tom.getX() < jerry.getX()) {
                vyhniSeStromu();
                tom.moveForward();
            }
        } else if (horizontalniRozdil > 0) {
            otocSeVlevo();
            while (gameplay.isGameRunning() && tom.getX() > jerry.getX()) {
                vyhniSeStromu();
                tom.moveForward();
            }
        }

        int vertikalniRozdil = tom.getY() - jerry.getY(); // záporný = Tom je nahoře od Jerryho, kladný = Tom je dole od Jerryho
        if (vertikalniRozdil < 0) {   //Tom je nahoře od Jerryho
            otocSeDolu();
            while (gameplay.isGameRunning() && tom.getY() < jerry.getY()) {
                vyhniSeStromu();
                tom.moveForward();
            }
        } else if (vertikalniRozdil > 0) {
            otocSeNahoru();
            while (gameplay.isGameRunning() && tom.getY() > jerry.getY()) {
                vyhniSeStromu();
                tom.moveForward();
            }
        }
    }

    private void vyhniSeStromu() {
        if (tom.isPossibleToMoveForward()) {
            return;
        }
        tom.turnRight();
        tom.moveForward();
        tom.turnLeft();
    }

    private void otocSeVpravo() {
        if (tom.getOrientation() == PlayerOrientation.RIGHT) {
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.UP) {
            tom.turnRight();
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.DOWN) {
            tom.turnLeft();
            return;
        }
        tom.turnLeft();
        tom.turnLeft();
    }

    private void otocSeVlevo() {
        if (tom.getOrientation() == PlayerOrientation.LEFT) {
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.UP) {
            tom.turnLeft();
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.DOWN) {
            tom.turnRight();
            return;
        }
        tom.turnLeft();
        tom.turnLeft();
    }

    private void otocSeNahoru() {
        if (tom.getOrientation() == PlayerOrientation.UP) {
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.LEFT) {
            tom.turnRight();
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.RIGHT) {
            tom.turnLeft();
            return;
        }
        tom.turnLeft();
        tom.turnLeft();
    }

    private void otocSeDolu() {
        if (tom.getOrientation() == PlayerOrientation.DOWN) {
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.LEFT) {
            tom.turnLeft();
            return;
        }
        if (tom.getOrientation() == PlayerOrientation.RIGHT) {
            tom.turnRight();
            return;
        }
        tom.turnLeft();
        tom.turnLeft();
    }

    private void handleUlozitAction(ActionEvent action) {
        Gameplay.getInstance().stopMovingAll();
        try {
            System.out.println("Ukládám stav do souboru…");
            uloziste.ulozitStavDoSouboru();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gameplay.getInstance().startMovingAll();
    }

    private void handleNacistAction(ActionEvent action) {
        Gameplay.getInstance().stopMovingAll();
        try {
            System.out.println("Načítám stav ze souboru…");
            uloziste.nacistStavZeSouboru();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gameplay.getInstance().startMovingAll();
    }
}
