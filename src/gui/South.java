package gui;

import javax.swing.*;
import java.awt.*;

public class South extends JPanel {
    JButton insertBtn;
    JButton updateBtn;
    JButton deleteBtn;

    public South() {
        super(new FlowLayout(FlowLayout.CENTER));
        insertBtn = new JButton("Insertar");
        updateBtn = new JButton("Actualizar");
        deleteBtn = new JButton("Eliminar");
        add(insertBtn);
        add(updateBtn);
        add(deleteBtn);
    }
}
