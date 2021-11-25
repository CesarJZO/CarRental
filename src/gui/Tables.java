package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Tables extends JPanel {
    private final JComboBox<String> comboTablas;
    private final JPanel tablePanel;
    private final JTable table;
    private final JScrollPane tableScroller;

    public Tables(String[] tablas) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        comboTablas = new JComboBox<>(tablas);
        table = new JTable();

        tablePanel = new JPanel();
        tableScroller = new JScrollPane(
                tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
//        table.getTableHeader().setReorderingAllowed(false);
        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);
        tableScroller.setPreferredSize(new Dimension(330,230));

        add(tableScroller);
        add(comboTablas);
    }

    public void fillTable(DefaultTableModel tableModel) {
        table.setModel(tableModel);
    }

    public JComboBox<String> getComboTablas() {
        return comboTablas;
    }
}
