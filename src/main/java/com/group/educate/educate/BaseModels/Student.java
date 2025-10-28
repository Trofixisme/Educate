//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels;

import java.util.concurrent.atomic.AtomicInteger;

public class Student extends User {

    static AtomicInteger userCounter = new AtomicInteger();

    int graduatingYear;
    String UniName;

    public Student(String password, String name, String email) {
        super(password, name, email, Statics.student);
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
