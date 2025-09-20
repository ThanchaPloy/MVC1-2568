package model;

public class Student {
    private String studentId;//8 หลัก->69
    private String prefix;
    private String firstName;
    private String lastName;
    private String birthDate;//yyyy-MM-dd
    private String school;
    private String email;
    private String curriculumCode;//8 หลัก

    public Student(String studentId, String prefix, String firstName, String lastName, String birthDate, 
            String school,String email, String curriculumCode) {
        this.studentId = studentId;
        this.prefix = prefix;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.school = school;
        this.email = email;
        this.curriculumCode = curriculumCode;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCurriculumCode() {
        return curriculumCode;
    }
    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    
}
