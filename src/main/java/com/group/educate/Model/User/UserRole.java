package com.group.educate.Model.User;

public enum UserRole {

    STUDENT(1),
    EMPLOYER(2),
    COMPANY(3),
    ADMIN(4);



    private final int ID;

    UserRole(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
