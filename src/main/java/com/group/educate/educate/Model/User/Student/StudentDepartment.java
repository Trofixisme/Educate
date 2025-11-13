package com.group.educate.educate.Model.User.Student;

import java.util.ArrayList;

public class StudentDepartment {
    private String departmentId;
    private String departmentName;
    private final ArrayList<StudentMajor> majors;

    public StudentDepartment(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.majors = new ArrayList<>();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ArrayList<StudentMajor> getMajors() {
        return majors;
    }


    public void addMajors(StudentMajor major) {
        majors.add(major);
    }
}
