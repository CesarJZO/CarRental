package gui;

import javax.swing.*;
import java.util.*;

/**
 * A general purpose form. You can create a form with n fields by naming them.
 *
 * @author CésarJZO
 */
public class Form extends JPanel {
    // TODO Create an inner class Format with an array of attributes (txtFld, ComboBox, CheckBox) for each field
    private final JPanel[] panels;
    private final JLabel[] labels;
    private final JTextField[] txtFields;
    private final int amount;
    private final int COLUMNS = 15;

    private South southBtns;

    /**
     * Creates a form depending on how many labels you put.
     *
     * @param labels Texts to be displayed on the labels.
     */
    public Form(String... labels) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        amount = labels.length;
        panels = new JPanel[amount];
        this.labels = new JLabel[amount];
        txtFields = new JTextField[amount];
        for (int i = 0; i < amount; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
            this.labels[i] = new JLabel(labels[i]);
            txtFields[i] = new JTextField(COLUMNS);
            panels[i].add(this.labels[i]);
            panels[i].add(txtFields[i]);
            add(panels[i]);
        }
        southBtns = new South();
    }

    /**
     * Clear the typed values of each text field of the form.
     */
    public void reset() {
        for (JTextField txtFld : txtFields)
            txtFld.setText("");
    }

    public String getInsertedValue(String key) {
        Map<String, String> inserted = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            inserted.put(labels[i].getText(), txtFields[i].getText());
        }
        return inserted.get(key);
    }


    public South getSouth() {
        return southBtns;
    }

    public JButton getInsBtn() {
        return southBtns.insertBtn;
    }

    public JButton getUpdBtn() {
        return southBtns.updateBtn;
    }

    public JButton getDelBtn() {
        return southBtns.deleteBtn;
    }

    /**
     * Returns the typed values with their description.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < txtFields.length; i++) {
            sb.append(labels[i].getText()).append(": ").append(txtFields[i].getText());
            if (i < txtFields.length - 1) sb.append('\n');
        }
        return sb.toString();
    }
}
