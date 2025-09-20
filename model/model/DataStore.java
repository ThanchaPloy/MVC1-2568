package model;

import java.util.*;
public class DataStore {
    public static List<Student> students = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Subject> subjects = new ArrayList<>();
    public static List<SubjectStructure> structures = new ArrayList<>();
    public static List<RegisteredSubject> registeredSubjects = new ArrayList<>();

    // static 
    static {
        //admin
        users.add(new User("admin", "adminpass", "admin"));
        //sample 10 students
        students.add(new Student("69000001", "Mr.", "Somchai", "Jaidee", "2008-05-10", "Suankularb School", "somchai01@example.com", "10000001"));
        students.add(new Student("69000002", "Ms.", "Sudarat", "Thongdee", "2007-11-02", "Satriwitthaya School", "sudarat02@example.com", "10000001"));
        students.add(new Student("69000003", "Mr.", "Pawarit", "Panthong", "2008-01-23", "Triamudom School", "pawarit03@example.com", "10000001"));
        students.add(new Student("69000004", "Mr.", "Noppon", "Srisuk", "2007-09-13", "Bangkok Christian College", "noppon04@example.com", "10000001"));
        students.add(new Student("69000005", "Ms.", "Piyada", "Wattanasuk", "2008-03-05", "Bodin School", "piyada05@example.com", "20000001"));
        students.add(new Student("69000006", "Mr.", "Nattawut", "Chantra", "2007-06-29", "Mahidol Wittayanusorn School", "nattawut06@example.com", "20000001"));
        students.add(new Student("69000007", "Ms.", "Sasithorn", "Warangkun", "2008-02-14", "Rachineebon School", "sasithorn07@example.com", "20000001"));
        students.add(new Student("69000008", "Mr.", "Suriya", "Boonsong", "2007-07-30", "Watsuthiwararam School", "suriya08@example.com", "20000001"));
        students.add(new Student("69000009", "Ms.", "Jittlada", "Kasemsuk", "2008-08-22", "Satit Chula School", "jittlada09@example.com", "10000001"));
        students.add(new Student("69000010", "Mr.", "Apichai", "Tangmun", "2007-10-17", "Assumption College", "apichai10@example.com", "20000001"));

        //add user for student username = studentId, password = "pass123"
        for (Student s : students) {
            users.add(new User(s.getStudentId(), "pass123", "student"));
        }

        //วิชา 10 วิชา (5 วิชาคณะ, 5 วิชาศึกษาทั่วไป, มี prerequisite 1 วิชา)
        subjects.add(new Subject("05500001", "Basic Mathematics", 3, "Dr. Anan", null));
        subjects.add(new Subject("05500002", "Basic Physics", 3, "Mr. Supot", null));
        subjects.add(new Subject("05500003", "Basic Chemistry", 3, "Mr. Piya", null));
        subjects.add(new Subject("05500004", "Basic Biology", 3, "Ms. Waraporn", "05500003")); // prerequisite: Basic Chemistry
        subjects.add(new Subject("05500005", "English Language", 2, "Ms. Somsri", null));
        subjects.add(new Subject("90690001", "Thai Language", 2, "Ms. Wanna", null));
        subjects.add(new Subject("90690002", "Social Studies", 2, "Mr. Weerachai", null));
        subjects.add(new Subject("90690003", "Health Education", 1, "Mr. Suthep", null));
        subjects.add(new Subject("90690004", "Physical Education", 1, "Mr. Kittisak", null));
        subjects.add(new Subject("90690005", "Art and Music", 2, "Ms. Jirawan", null));

        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "05500001", 1));
        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "05500002", 1));
        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "05500003", 1));
        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "05500004", 2));
        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "05500005", 2));
        structures.add(new SubjectStructure("10000001", "Applied Science", "Faculty of Science", "90690001", 2));

        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "90690002", 1));
        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "90690003", 1));
        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "90690004", 1));
        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "90690005", 2));
        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "05500005", 2));
        structures.add(new SubjectStructure("20000001", "Fine Arts", "Faculty of Fine Arts", "05500001", 2));

        //เพิ่มตัวอย่างการลงทะเบียนและเกรด
        registeredSubjects.add(new RegisteredSubject("69000001", "05500001", "A"));
        registeredSubjects.add(new RegisteredSubject("69000001", "05500002", "B+"));
        registeredSubjects.add(new RegisteredSubject("69000002", "05500001", "A"));
        registeredSubjects.add(new RegisteredSubject("69000002", "90690001", "B"));
        registeredSubjects.add(new RegisteredSubject("69000003", "05500003", "C+"));
        registeredSubjects.add(new RegisteredSubject("69000003", "05500004", null)); // ยังไม่ได้เกรด
        registeredSubjects.add(new RegisteredSubject("69000004", "90690002", "A"));
        registeredSubjects.add(new RegisteredSubject("69000005", "90690003", "B"));
        registeredSubjects.add(new RegisteredSubject("69000006", "90690004", "A"));
        registeredSubjects.add(new RegisteredSubject("69000007", "90690005", null)); // ยังไม่ได้เกรด
        registeredSubjects.add(new RegisteredSubject("69000008", "05500005", "C"));
        registeredSubjects.add(new RegisteredSubject("69000009", "05500001", "B"));
        registeredSubjects.add(new RegisteredSubject("69000010", "90690001", "A"));
    }
}