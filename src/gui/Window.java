package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A simplified JFrame extended class that creates a dynamic window for general purposes
 *
 * @author CésarJZO
 */
public class Window extends JFrame {
    private final Dimension dimension;
    private JPanel mainPanel;
    private final ImageIcon icon;

    /**
     * Creates an empty visible window with a default title.
     * Resizable by default.
     */
    public Window() {
        this("Window");
    }

    /**
     * Creates an empty, visible and resizable window.
     *
     * @param title The String to be placed at the top of the window.
     */
    public Window(String title) {
        super(title);
        dimension = new Dimension(600, 400);
        setSize(dimension);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
//        mainPanel.setSize(dimension);
        setContentPane(mainPanel);

        icon = new ImageIcon("src/gui/images/database_flat.png");
        setIconImage(icon.getImage());
    }

    /**
     * Creates an empty visible window with a specified dimension, and it's not resizable.
     *
     * @param width  The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    public Window(int width, int height) {
        super("Window");
        dimension = new Dimension(width, height);
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setSize(dimension);
        setContentPane(mainPanel);
        icon = new ImageIcon("src/cesarjzo/gui/images/icon.png");
        setIconImage(icon.getImage());
    }

    /**
     * Creates an empty visible window.
     *
     * @param title  The String to be displayed in the window's border.
     * @param width  The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    public Window(String title, int width, int height) {
        super(title);
        dimension = new Dimension(width, height);
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setSize(dimension);
        setContentPane(mainPanel);
        icon = new ImageIcon("src/cesarjzo/gui/images/icon.png");
        setIconImage(icon.getImage());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Sets mainPanel and puts it into the contentPane.
     *
     * @param mainPanel Panel to be the main container.
     */
    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setContentPane(mainPanel);
    }

    /**
     * Makes this Window visible and fits it depending on the content.
     */
    public void appearAndFit() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
