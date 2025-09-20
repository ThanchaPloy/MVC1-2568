import model.DataStore;
import model.User;
import model.Student;
import view.LoginView;
import view.StudentListView;
import view.StudentProfileView;
import controller.AuthController;
import controller.AdminController;
import controller.StudentController;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;


public class RegisterApp {
    public static void main(String[] args) {
        //เริ่มLogin
        LoginView loginView = new LoginView();
        AuthController authController = new AuthController(loginView);

        //รอ user login
        while (authController.loggedUser == null) {
            try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        User user = authController.loggedUser;

        //ถ้า role admin->StudentListView
        if ("admin".equals(user.getRole())) {
            List<String[]> studentRows = DataStore.students.stream().map(s -> new String[]{
                    s.getStudentId(),
                    s.getPrefix(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getBirthDate(),
                    s.getSchool(),
                    s.getCurriculumCode()
            }).collect(Collectors.toList());
            List<String> schoolList = DataStore.students.stream()
                    .map(Student::getSchool)
                    .distinct()
                    .collect(Collectors.toList());
            StudentListView studentListView = new StudentListView(studentRows, schoolList);
            AdminController adminController = new AdminController(studentListView);

        // ถ้าrole student->StudentProfileView
        } else if ("student".equals(user.getRole())) {
            Student student = DataStore.students.stream()
                    .filter(s -> s.getStudentId().equals(user.getUsername()))
                    .findFirst().orElse(null);

            List<model.RegisteredSubject> regSub = DataStore.registeredSubjects.stream()
                    .filter(r -> r.getStudentId().equals(student.getStudentId()))
                    .collect(Collectors.toList());

            Object[][] subjectData = regSub.stream().map(rs -> {
                model.Subject sub = DataStore.subjects.stream()
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

            StudentProfileView studentProfileView = new StudentProfileView(info, subjectData);
            StudentController studentController = new StudentController(studentProfileView, student);

        } else {
            JOptionPane.showMessageDialog(null, "User role not found!");
        }
    }
}