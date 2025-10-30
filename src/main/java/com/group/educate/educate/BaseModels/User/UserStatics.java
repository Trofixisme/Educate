//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels.User;

import java.lang.instrument.IllegalClassFormatException;

public class UserStatics {

    static UserRole admin;
    static UserRole student;
    static UserRole employer;
    static UserRole company;
    static UserRole companyEmployer;

    static {
        try {
            admin = new UserRole("admin");
            student = new UserRole("student");
            employer = new UserRole("employer");
            company = new UserRole("company");
            companyEmployer = new UserRole("Company Employer");
        } catch (IllegalClassFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
