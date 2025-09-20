package model;

public class SubjectStructure {
    private String curriculumCode;  //8หลัก
    private String curriculumName;
    private String department;
    private String requiredSubjectId;
    private int semester; // 1 หรือ 2

    public SubjectStructure(String curriculumCode, String curriculumName, String department, String requiredSubjectId, int semester) {
        this.curriculumCode = curriculumCode;
        this.curriculumName = curriculumName;
        this.department = department;
        this.requiredSubjectId = requiredSubjectId;
        this.semester = semester;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }
    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }
    public String getCurriculumName() {
        return curriculumName;
    }
    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getRequiredSubjectId() {
        return requiredSubjectId;
    }
    public void setRequiredSubjectId(String requiredSubjectId) {
        this.requiredSubjectId = requiredSubjectId;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }

    
}
