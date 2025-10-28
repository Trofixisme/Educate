//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels;

import java.lang.instrument.IllegalClassFormatException;

public class Statics {

    static UserRole admin;
    static UserRole student;
    static UserRole employer;

    static {
        try {
            admin = new UserRole("admin");
            student = new UserRole("student");
            employer = new UserRole("employer");
        } catch (IllegalClassFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
