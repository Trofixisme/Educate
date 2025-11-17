package com.group.educate.Model.User.Student;

import java.util.ArrayList;

public class StudentMajor {
    private static int counter;
    private final int majorId;
    private String majorName;
    // major has many students
    private ArrayList<Student> students= new ArrayList();

    public StudentMajor( String majorName) {
        this.majorId =++counter;
        this.majorName = majorName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    public void addStudents(Student student) {
        students.add(student);
    }

    public int getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }
}
