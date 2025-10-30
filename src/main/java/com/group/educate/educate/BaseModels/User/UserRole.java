//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels.User;

import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRole {

    private static AtomicInteger counter = new AtomicInteger();

    private static HashMap<Integer, String> roles = new HashMap<>();

    private String roleName;
    private int id = counter.incrementAndGet();

    public UserRole(String roleName) throws IllegalClassFormatException {
        roleName = roleName.toLowerCase();
            if (roles.containsValue(roleName)) {
                throw new IllegalClassFormatException("A role with the same id or name already exists");
            } else {
                this.roleName = roleName;
                roles.put(id, roleName);
            }
    }

    protected int getRoleID() {
        return id;
    }

    protected String getRoleName() {
        return roleName;
    }
}
