//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class User {

    //TODO: Somehow resolve the issue causing the various objects extending this class to have unique IDs when they shouldn't

    static AtomicInteger userCounter = new AtomicInteger();
    private final int userID = userCounter.incrementAndGet();
    private String password;
    private String name;
    private String email;

    ArrayList<UserRole> userRoles = new ArrayList<>();

    public User(String password, String name, String email, UserRole userRole) {
        setPassword(password);
        setName(name);
        setEmail(email);
        userRoles.add(userRole);
    }

    public int getUserID() {
        return userID;
    }

    //TODO: do some validation before changing the password

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    //TODO: Also add some validation when setting the user's name (Why do we even need to do that??)

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    //TODO: do some ACTUAL validation before changing the user's email

    public void setEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            //I don't think we should be throwing errors in the servers,
            //So someone might be required to change that later on.
            throw new IllegalArgumentException("Provided email isn't valid");
        }
    }
}
