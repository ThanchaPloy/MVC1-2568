package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentListView extends JFrame {
    public JTable table;
    public JTextField searchField = new JTextField(15);
    public JComboBox<String> schoolFilter;
    public JComboBox<String> sortCombo;
    public JButton viewProfileBtn = new JButton("View Profile");
    public JButton gradeBtn = new JButton("Grade Entry");
    public DefaultTableModel tableModel;

    public StudentListView(List<String[]> studentRows, List<String> schoolList) {
        setTitle("Student List (Admin)");
        setSize(900, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search Name:"));
        topPanel.add(searchField);

        schoolFilter = new JComboBox<>();
        schoolFilter.addItem("All Schools");
        for (String school : schoolList) schoolFilter.addItem(school);
        topPanel.add(new JLabel("Filter by School:"));
        topPanel.add(schoolFilter);

        sortCombo = new JComboBox<>(new String[]{"Sort by Name", "Sort by Age"});
        topPanel.add(sortCombo);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Student ID", "Prefix", "First Name", "Last Name", "Birthdate", "School", "Curriculum"};
        tableModel = new DefaultTableModel(columns, 0);
        for (String[] row : studentRows) tableModel.addRow(row);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(viewProfileBtn);
        bottomPanel.add(gradeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}