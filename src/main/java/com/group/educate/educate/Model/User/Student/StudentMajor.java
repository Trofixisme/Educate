package com.group.educate.educate.Model.User.Student;

public class StudentMajor {
    private String majorId;
    private String majorName;

    public StudentMajor(String majorId, String majorName) {
        this.majorId = majorId;
        this.majorName = majorName;
    }

    public String getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }
}
