package com.group.educate.Model.User.Student;

import java.util.ArrayList;

public class StudentMajor {
    private String majorId;
    private String majorName;
    // major has many students
    private ArrayList<Student> students= new ArrayList();

    public StudentMajor(String majorId, String majorName) {
        this.majorId = majorId;
        this.majorName = majorName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    public void addStudents(Student student) {
        students.add(student);
    }

    public String getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }
}
