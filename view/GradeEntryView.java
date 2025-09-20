package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GradeEntryView extends JFrame {
    public JTable regTable;
    public DefaultTableModel regTableModel;
    public JButton saveBtn = new JButton("Save Grades");
    public JLabel countLabel = new JLabel();

    public GradeEntryView(Object[][] regRows, int studentCount) {
        setTitle("Enter Grades");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        countLabel.setText("Number of registered students: " + studentCount);
        add(countLabel, BorderLayout.NORTH);

        String[] columns = {"Student ID", "Name", "Subject ID", "Subject Name", "Current Grade", "New Grade"};
        regTableModel = new DefaultTableModel(columns, 0);
        for (Object[] row : regRows) regTableModel.addRow(row);
        regTable = new JTable(regTableModel);

        add(new JScrollPane(regTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}