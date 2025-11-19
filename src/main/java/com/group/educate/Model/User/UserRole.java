package com.group.educate.Model.User;

public enum UserRole {

    STUDENT(1),
    EMPLOYER(2),
    ADMIN(3);

    private final int ID;

    UserRole(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
