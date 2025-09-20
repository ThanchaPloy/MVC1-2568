package controller;

import model.DataStore;
import model.Student;
import model.Subject;
import model.SubjectStructure;
import model.RegisteredSubject;
import view.StudentProfileView;
import view.RegisterSubjectView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentController {
    private StudentProfileView view;
    private Student student;

    public StudentController(StudentProfileView view, Student student) {
        this.view = view;
        this.student = student;

        view.registerBtn.addActionListener(e -> showRegisterSubject());
    }

    //คำนวณอายุนักเรียน
    private int calculateAge(String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(birthDate, formatter);
            return Period.between(dob, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }

    private void showRegisterSubject() {
        //นักเรียนต้องมีอายุ ≥ 15 ปี
        int age = calculateAge(student.getBirthDate());
        if (age < 15) {
            JOptionPane.showMessageDialog(view, "You must be at least 15 years old to register subjects.");
            return;
        }

        Set<String> alreadyRegistered = DataStore.registeredSubjects.stream()
                .filter(rs -> rs.getStudentId().equals(student.getStudentId()))
                .map(RegisteredSubject::getSubjectId)
                .collect(Collectors.toSet());

        List<SubjectStructure> requiredStructures = DataStore.structures.stream()
                .filter(st -> st.getCurriculumCode().equals(student.getCurriculumCode()))
                .collect(Collectors.toList());

        List<Subject> subjectCanRegister = requiredStructures.stream()
                .map(st -> DataStore.subjects.stream()
                        .filter(sub -> sub.getSubjectId().equals(st.getRequiredSubjectId()))
                        .findFirst().orElse(null))
                .filter(sub -> sub != null && !alreadyRegistered.contains(sub.getSubjectId()))
                .collect(Collectors.toList());

        Object[][] subjectRows = subjectCanRegister.stream()
                .map(sub -> new Object[]{
                        sub.getSubjectId(),
                        sub.getSubjectName(),
                        sub.getCredit(),
                        sub.getTeacher(),
                        sub.getPrerequisiteSubjectId() != null ? sub.getPrerequisiteSubjectId() : "-"
                }).toArray(Object[][]::new);

        RegisterSubjectView registerView = new RegisterSubjectView(subjectRows);

        //ลงทะเบียน
        registerView.registerBtn.addActionListener(e -> {
            int selectedRow = registerView.subjectTable.getSelectedRow();
            if (selectedRow >= 0) {
                String subjectId = (String) registerView.subjectTableModel.getValueAt(selectedRow, 0);

                //จำนวนนักเรียนที่ลงทะเบียนวิชา ≥ 0
                long count = DataStore.registeredSubjects.stream()
                        .filter(rs -> rs.getSubjectId().equals(subjectId))
                        .count();
                if (count < 0) {
                    JOptionPane.showMessageDialog(registerView, "Registration count error!");
                    return;
                }

                //ตรวจสอบ prerequisite
                Subject sub = DataStore.subjects.stream()
                        .filter(s -> s.getSubjectId().equals(subjectId))
                        .findFirst().orElse(null);
                boolean canRegister = true;
                if (sub != null && sub.getPrerequisiteSubjectId() != null) {
                    Optional<RegisteredSubject> prereq =
                            DataStore.registeredSubjects.stream()
                                    .filter(rs -> rs.getStudentId().equals(student.getStudentId())
                                            && rs.getSubjectId().equals(sub.getPrerequisiteSubjectId()))
                                    .findFirst();
                    //มีการลงทะเบียนวิชาบังคับก่อน และต้องมีเกรดแล้วเท่านั้น(เกรด != null)
                    if (!prereq.isPresent() || prereq.get().getGrade() == null) {
                        canRegister = false;
                    }
                }
                if (canRegister) {
                    DataStore.registeredSubjects.add(new RegisteredSubject(student.getStudentId(), subjectId, null));
                    JOptionPane.showMessageDialog(registerView, "Registered successfully!");
                    registerView.dispose();
                    updateStudentProfileSubjects(); // กลับไปหน้าโปรไฟล์และอัปเดตทันที
                } else {
                    JOptionPane.showMessageDialog(registerView, "Prerequisite not passed or not graded yet!");
                }
            }
        });

        registerView.setVisible(true);
    }

    //อัปเดตตารางวิชาในโปรไฟล์หลังลงทะเบียนใหม่
    private void updateStudentProfileSubjects() {
        List<RegisteredSubject> regSub = DataStore.registeredSubjects.stream()
                .filter(r -> r.getStudentId().equals(student.getStudentId()))
                .collect(Collectors.toList());

        Object[][] subjectData = regSub.stream().map(rs -> {
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

        view.subjectTableModel.setRowCount(0);
        for (Object[] row : subjectData) view.subjectTableModel.addRow(row);
    }
}