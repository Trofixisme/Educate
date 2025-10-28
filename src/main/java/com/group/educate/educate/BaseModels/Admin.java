package com.group.educate.educate.BaseModels;

public class Admin extends User {

    private PermissionLevel permissionLevel;

    public Admin(String password, String name, String email) {
        super(password, name, email, Statics.admin);
        permissionLevel = PermissionLevel.Low;
    }

    public Admin(String password, String name, String email, PermissionLevel permissionLevel) {
        super(password, name, email, Statics.admin);
        this.permissionLevel = permissionLevel;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }
}

