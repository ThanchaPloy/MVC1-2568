package controller;

import model.DataStore;
import model.Student;
import view.StudentListView;
import view.StudentProfileView;
import view.GradeEntryView;
import model.RegisteredSubject;
import model.Subject;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdminController {
    private StudentListView view;
    private List<Student> studentList = DataStore.students;

    public AdminController(StudentListView view) {
        this.view = view;

        // Event filter/search/sort
        view.searchField.addActionListener(e -> filterAndUpdateTable());
        view.schoolFilter.addActionListener(e -> filterAndUpdateTable());
        view.sortCombo.addActionListener(e -> filterAndUpdateTable());

        // Event ดู profile นักเรียน
        view.viewProfileBtn.addActionListener(e -> {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow >= 0) {
                String studentId = (String) view.tableModel.getValueAt(selectedRow, 0);
                Student student = studentList.stream()
                        .filter(s -> s.getStudentId().equals(studentId))
                        .findFirst().orElse(null);
                if (student != null) {
                    showStudentProfile(student);
                }
            }
        });

        // Event กรอกเกรด
        view.gradeBtn.addActionListener(e -> {
            String subjectId = JOptionPane.showInputDialog(view, "Enter Subject ID to grade:");
            if (subjectId != null && !subjectId.isEmpty()) {
                openGradeEntry(subjectId);
            }
        });

        // Double click เพื่อดูโปรไฟล์
        view.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = view.table.getSelectedRow();
                    String studentId = (String) view.tableModel.getValueAt(selectedRow, 0);
                    Student student = studentList.stream()
                            .filter(s -> s.getStudentId().equals(studentId))
                            .findFirst().orElse(null);
                    if (student != null) {
                        showStudentProfile(student);
                    }
                }
            }
        });
    }

    // ฟิลเตอร์/เรียงลำดับตารางนักเรียน
    private void filterAndUpdateTable() {
        String search = view.searchField.getText().trim().toLowerCase();
        String school = (String) view.schoolFilter.getSelectedItem();
        String sortBy = (String) view.sortCombo.getSelectedItem();

        List<Student> filtered = studentList.stream()
                .filter(s -> search.isEmpty() ||
                        s.getFirstName().toLowerCase().contains(search) ||
                        s.getLastName().toLowerCase().contains(search))
                .filter(s -> school.equals("All Schools") || s.getSchool().equals(school))
                .collect(Collectors.toList());

        if ("Sort by Name".equals(sortBy)) {
            filtered.sort(Comparator.comparing(Student::getFirstName));
        } else if ("Sort by Age".equals(sortBy)) {
            filtered.sort(Comparator.comparing(Student::getBirthDate));
        }

        view.tableModel.setRowCount(0);
        for (Student s : filtered) {
            view.tableModel.addRow(new String[]{
                    s.getStudentId(),
                    s.getPrefix(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getBirthDate(),
                    s.getSchool(),
                    s.getCurriculumCode()
            });
        }
    }

    // เปิดดูประวัตินักเรียน
    private void showStudentProfile(Student student) {
        List<RegisteredSubject> regSubs = DataStore.registeredSubjects.stream()
                .filter(r -> r.getStudentId().equals(student.getStudentId()))
                .collect(Collectors.toList());
        Object[][] subjectRows = regSubs.stream().map(rs -> {
            Subject sub = DataStore.subjects.stream()
                    .filter(s -> s.getSubjectId().equals(rs.getSubjectId()))
                    .findFirst().orElse(null);
            return new Object[]{
                    rs.getSubjectId(),
                    sub != null ? sub.getSubjectName() : "",
                    sub != null ? sub.getCredit() : "",
                    rs.getGrade() != null ? rs.getGrade() : "Not graded yet"
            };
        }).toArray(Object[][]::new);

        String info = student.getPrefix() + " " + student.getFirstName() + " " + student.getLastName()
                + " | School: " + student.getSchool() + " | Email: " + student.getEmail();

        StudentProfileView profileView = new StudentProfileView(info, subjectRows);
        profileView.setVisible(true);
    }

    // เปิดหน้ากรอกเกรดและอัปเดตข้อมูล
    public void openGradeEntry(String subjectId) {
        List<RegisteredSubject> regs = DataStore.registeredSubjects.stream()
                .filter(r -> r.getSubjectId().equals(subjectId))
                .collect(Collectors.toList());
        int studentCount = regs.size();
        Object[][] regRows = regs.stream().map(r -> {
            Student s = DataStore.students.stream()
                    .filter(stu -> stu.getStudentId().equals(r.getStudentId()))
                    .findFirst().orElse(null);
            Subject sub = DataStore.subjects.stream()
                    .filter(sb -> sb.getSubjectId().equals(r.getSubjectId()))
                    .findFirst().orElse(null);
            return new Object[]{
                    r.getStudentId(),
                    s != null ? s.getFirstName() + " " + s.getLastName() : "",
                    r.getSubjectId(),
                    sub != null ? sub.getSubjectName() : "",
                    r.getGrade() != null ? r.getGrade() : "",
                    ""
            };
        }).toArray(Object[][]::new);

        GradeEntryView gradeView = new GradeEntryView(regRows, studentCount);

        gradeView.saveBtn.addActionListener(e -> {
            for (int i = 0; i < gradeView.regTableModel.getRowCount(); i++) {
                String studentId = (String) gradeView.regTableModel.getValueAt(i, 0);
                String newGrade = (String) gradeView.regTableModel.getValueAt(i, 5);
                if (newGrade != null && !newGrade.isEmpty()) {
                    DataStore.registeredSubjects.stream()
                            .filter(rg -> rg.getStudentId().equals(studentId) && rg.getSubjectId().equals(subjectId))
                            .findFirst()
                            .ifPresent(rg -> rg.setGrade(newGrade));
                }
            }
            JOptionPane.showMessageDialog(gradeView, "Grades saved.");
            gradeView.dispose(); // ปิดหน้าต่างกรอกเกรด
            filterAndUpdateTable(); // อัปเดตตารางหลัก

            // เปิดดูประวัตินักเรียนคนแรกที่กรอกเกรด
            if (studentCount > 0) {
                String studentId = (String) gradeView.regTableModel.getValueAt(0, 0);
                Student student = DataStore.students.stream()
                    .filter(s -> s.getStudentId().equals(studentId))
                    .findFirst().orElse(null);
                if (student != null) showStudentProfile(student);
            }
        });

        gradeView.setVisible(true);
    }
}