package gui;

import javax.swing.*;
import java.awt.*;

public class Central extends JPanel {
    private final JPanel center;
    private final JPanel east;
    private final JPanel south;
    private final Form[] formArray;
    private final South[] southArray;

    public JList<String> list;

    public Central(Form... forms) {
        super(new BorderLayout());
        center = new JPanel();
        east = new JPanel();
        south = new JPanel();
        JScrollPane centerScroller = new JScrollPane(
                center,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        centerScroller.setPreferredSize(new Dimension(240, 200));

        formArray = new Form[forms.length];
        southArray = new South[forms.length];
        for (int i = 0; i < forms.length; i++) {
            center.add(forms[i]);
            formArray[i] = forms[i];
            southArray[i] = forms[i].getSouth();
            south.add(southArray[i]);
        }

        add(centerScroller, BorderLayout.CENTER);
        add(east, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
    }
    
    public void setVisibleOnly(Form form) {
        for (Form f : formArray)
            f.setVisible(f.equals(form));
        for (Form f : formArray)
            f.getSouth().setVisible(f.getSouth().equals(form.getSouth()));
    }

    public void addList(String... tables) {
        list = new JList<>(tables);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);

//        JScrollPane listScroller = new JScrollPane(list);
        east.add(list);
    }
}
