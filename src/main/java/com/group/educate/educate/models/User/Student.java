//Created by Ziad on 28/10/2025

package com.group.educate.educate.models.User;

public class Student extends User {

    int graduatingYear;
    String UniName;

    public Student(String password, String name, String email) {
        super(password, name, email);
    }

    public int getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(int graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public String getUniName() {
        return UniName;
    }
}
