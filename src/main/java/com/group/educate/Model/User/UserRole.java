package com.group.educate.Model.User;

public enum UserRole {

    STUDENT(1),
    EMPLOYER(2),
    COMPANY(3),
    ADMIN(4);



    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
