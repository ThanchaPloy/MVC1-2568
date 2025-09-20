package model;

public class Subject {
    private String subjectId;//8 หลัก
    private String subjectName;
    private int credit;// >0
    private String teacher;
    private String prerequisiteSubjectId; //nullable

    public Subject(String subjectId, String subjectName, int credit, String teacher, String prerequisiteSubjectId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.teacher = teacher;
        this.prerequisiteSubjectId = prerequisiteSubjectId;
    }
    
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getPrerequisiteSubjectId() {
        return prerequisiteSubjectId;
    }
    public void setPrerequisiteSubjectId(String prerequisiteSubjectId) {
        this.prerequisiteSubjectId = prerequisiteSubjectId;
    }

    
}
