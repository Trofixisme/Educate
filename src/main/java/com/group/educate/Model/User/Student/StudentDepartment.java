package com.group.educate.Model.User.Student;

import java.util.ArrayList;

public class StudentDepartment {
    private static int counter;
    private int departmentId;
    private String departmentName;
    private final ArrayList<StudentMajor> majors;

    public StudentDepartment( String departmentName) {
        this.departmentId =++counter;
        this.departmentName = departmentName;
        this.majors = new ArrayList<>();
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
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
    public void removeMajors(StudentMajor major) {
        majors.remove(major);
    }
}
