package cz.czechitas.lekce10.engine.swing;

import net.miginfocom.swing.MigLayout;
import net.sevecek.util.swing.JKeyboard;
import net.sevecek.util.swing.SwingExceptionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    public static final String WINDOW_TITLE = "Game Engine";
    private static MainWindow instance;
    private MigLayout migLayoutManager;
    private JPanel contentPane;
    private JKeyboard keyboard;
    private JLabel message;
    private JPanel gamepad;

    private JButton ulozitStavButton;
    private JButton nacistStavButton;

    public synchronized static MainWindow getInstance() {
        if (instance == null) {
            SwingExceptionHandler.install();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Unable to set platform look and feel for Swing");
            }
            instance = new MainWindow(WINDOW_TITLE);
            instance.setVisible(true);
        }
        return instance;
    }

    private MainWindow(String title) {
        super(title);
        initComponents();
    }

    private static GraphicsConfiguration getScreenConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        contentPane = (JPanel) getContentPane();
        contentPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onWindowResized(e);
            }
        });

        gamepad = initGamepad();

        contentPane.setLayout(new BorderLayout());
        contentPane.add(gamepad, BorderLayout.CENTER);
        contentPane.add(initButtonBar(), BorderLayout.SOUTH);

        keyboard = new JKeyboard();

        pack();

        String screen = System.getenv("SCREEN");
        Component anchor = null;
        if (screen != null) {
            try {
                int screenNumber = Integer.parseInt(screen);
                GraphicsDevice[] devices = GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getScreenDevices();
                if (screenNumber < 0 || screenNumber >= devices.length) {
                    System.err.printf("Chybné číslo obrazovky: '%s'.", screen).println();
                } else {
                    GraphicsConfiguration configuration = devices[screenNumber].getDefaultConfiguration();
                    anchor = new JFrame(configuration);
                }
            } catch (NumberFormatException e) {
                System.err.printf("Chybný formát proměnné prostředí SCREEN: '%s'.", screen).println();
            }
            setLocationRelativeTo(null);
        }
        setLocationRelativeTo(anchor);
    }

    private JPanel initGamepad() {
        JPanel contentPane = new JPanel();

        String singleCell = "[50px:50px:50px,fill]";
        String rowConstraints = "";
        for (int i = 0; i < 12; i++) {
            rowConstraints += singleCell;
        }
        String columnConstraints = "";
        for (int i = 0; i < 20; i++) {
            columnConstraints += singleCell;
        }

        migLayoutManager = new MigLayout(
                "insets 0,hidemode 3,gap 0px",
                // columns
                columnConstraints,
                // rows
                rowConstraints);
        contentPane.setLayout(migLayoutManager);
        contentPane.setBackground(this.getBackground());

        message = new JLabel();
        Font font = message.getFont().deriveFont(50.0F);
        message.setFont(font);
        contentPane.add(message, "pos 50% 50%");
        contentPane.setComponentZOrder(message, 0);

        return contentPane;
    }

    private JPanel initButtonBar() {
        JPanel bar = new JPanel();

        ulozitStavButton = new JButton("Uložit stav");
        nacistStavButton = new JButton("Načíst stav");
        bar.add(ulozitStavButton);
        bar.add(nacistStavButton);
        return bar;
    }
    private void onWindowResized(ComponentEvent evt) {
        setTitle(WINDOW_TITLE + " [Size: " + contentPane.getWidth() + " x " + contentPane.getHeight() + "]");
    }

    public void addWindowClosingListener(Runnable listener) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listener.run();
            }
        });
    }

    public JKeyboard getKeyboard() {
        return keyboard;
    }

    public JPanel getGamepad() {
        return gamepad;
    }

    public void showMessage(String text) {
        message.setText(text);
        Dimension size = message.getPreferredSize();
        migLayoutManager.setComponentConstraints(message, "pos 50%-" + size.getWidth() / 2.0 + "px 50%-" + size.getHeight() / 2.0 + "px");
        migLayoutManager.invalidateLayout(contentPane);
        repaint();
    }

    public JButton getUlozitStavButton() {
        return ulozitStavButton;
    }

    public JButton getNacistStavButton() {
        return nacistStavButton;
    }

}
