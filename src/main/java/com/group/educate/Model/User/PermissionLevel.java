package com.group.educate.Model.User;

public enum PermissionLevel {
    
    LOW(0),
    MEDIUM(1),
    HIGH(2),
    COMPLETE_ACCESS(3);

    final int ID;

    PermissionLevel(int id) {
        ID = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
