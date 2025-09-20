package view;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class RegisterSubjectView extends JFrame {
    public JTable subjectTable;
    public DefaultTableModel subjectTableModel;
    public JButton registerBtn = new JButton("Register Selected Subject");

    public RegisterSubjectView(Object[][] subjectRows) {
        setTitle("Register Subject");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Subject ID", "Name", "Credit", "Teacher", "Prerequisite"};
        subjectTableModel = new DefaultTableModel(columns, 0);
        for (Object[] row : subjectRows) subjectTableModel.addRow(row);
        subjectTable = new JTable(subjectTableModel);

        add(new JScrollPane(subjectTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(registerBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}