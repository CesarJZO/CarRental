package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A generic login user panel with username and password
 *
 * @author CésarJZO
 */
public class Login extends JPanel {
    public static final int ENGLISH = 0, SPANISH = 1;
    private final int COLUMNS = 15;
    private final int language;
    private final JPanel userPanel;
    private final JPanel passwordPanel;
    private final JPanel buttonPanel;
    private JLabel userLabel, passwordLabel;
    private final JTextField userField;
    private final JPasswordField passwordField;
    private JButton sendButton;

    /**
     * Creates the login panel (english by default);
     */
    public Login() {
        this(ENGLISH);
    }

    /**
     * Creates the login panel with a specified language.
     *
     * @param language The language to be used
     */
    public Login(int language) {
        this.language = language;
        setLabels();

        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userField = new JTextField(COLUMNS);
        userPanel.add(userLabel);
        userPanel.add(userField);

        passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordField = new JPasswordField(COLUMNS);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(userPanel);
        this.add(passwordPanel);
        this.add(buttonPanel);
    }


    public JButton getButton() {
        return sendButton;
    }

    public String getUser() {
        return userField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public boolean isEmpty() {
        return userField.getText().isEmpty() || passwordField.getText().isEmpty();
    }

    /**
     * Sets the label's language at the specified language.
     */
    private void setLabels() {
        if (language == 1) {
            userLabel = new JLabel("Usuario");
            passwordLabel = new JLabel("Contraseña");
            sendButton = new JButton("Enviar");
        } else {
            userLabel = new JLabel("User");
            passwordLabel = new JLabel("Password");
            sendButton = new JButton("Login");
        }
    }

    public void clean() {
        userField.setText("");
        passwordField.setText("");
    }

    /**
     * Makes a string using the username and the password.
     *
     * @return A string with the user and the password.
     */
    public String toString() {
        return "Username: " + userField.getText() + "\nPassword: " + passwordField.getText();
    }
}
