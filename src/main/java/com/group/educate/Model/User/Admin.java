package com.group.educate.Model.User;

public class Admin extends User {

    public Admin(String fname, String lname, String email, String plainPassword,UserRole role) {
        super(fname, lname,  email, plainPassword,role);
    }
}

