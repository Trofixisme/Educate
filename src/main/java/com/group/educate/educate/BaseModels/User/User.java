//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels.User;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

class User {

    //TODO: Somehow resolve the issue causing the various objects extending this class to have unique IDs when they shouldn't
    //Update: Issue might've been resolved. No testing has been conducted yet
    //update made the id as an integer

    private static int counter=0;
    private String password;
    private String name;
    private String email;
    private final int userId;


    public User(String password, String name, String email) {
        userId=++counter;
        setPassword(password);
        setName(name);
        setEmail(email);

    }

    public int getUserID() {
        return userId;
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
