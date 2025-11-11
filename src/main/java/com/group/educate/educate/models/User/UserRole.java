package com.group.educate.educate.models.User;

public enum UserRole {
    STUDENT(1),
    EMPLOYER(2),
    COMPANY(3);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
