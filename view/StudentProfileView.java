package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentProfileView extends JFrame {
    public JLabel infoLabel = new JLabel();
    public JTable subjectTable;
    public DefaultTableModel subjectTableModel;
    public JButton registerBtn = new JButton("Register Subject");
    public JButton refreshBtn = new JButton("Refresh");

    public StudentProfileView(String info, Object[][] subjectRows) {
        setTitle("Student Profile");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        infoLabel.setText(info);
        add(infoLabel, BorderLayout.NORTH);

        String[] columns = {"Subject ID", "Name", "Credit", "Grade"};
        subjectTableModel = new DefaultTableModel(columns, 0);
        for (Object[] row : subjectRows) subjectTableModel.addRow(row);
        subjectTable = new JTable(subjectTableModel);

        add(new JScrollPane(subjectTable), BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(registerBtn);
        southPanel.add(refreshBtn);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}