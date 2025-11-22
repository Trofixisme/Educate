//Created by Ziad on 28/10/2025

package com.group.educate.Model.User;

import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {

    private String hashedPassword;
    private String fname;
    private String lname;
    private String email;
    private final UUID userID;
    private final UserRole role;

    public User(String fname, String lname, String email, String plainPassword, UserRole role) {
        userID = UUID.randomUUID();
        hashPassword(plainPassword);
        setFName(fname);
        setLName(lname);
        setEmail(email);
        this.role = role;
    }

    public User(String userID, String fname, String lname, String email, String plainPassword, UserRole role) {
        this.userID = UUID.fromString(userID);
        hashPassword(plainPassword);
        setFName(fname);
        setLName(lname);
        setEmail(email);
        this.role = role;
    }

    //Hash password with BCrypt
    public void hashPassword(String plainPassword) { hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); }

    //Check if the input password matches the stored hash
    public boolean verifyPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.hashedPassword);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getUserID() {
        return userID.toString();
    }

    public String getFname() {
        return fname;
    }

    //TODO: Also add some validation when setting the user's name (Why do we even need to do that??)

    public void setFName(String fName) {
        this.fname = fName;
    }

    public String getLname() {
        return lname;
    }

    public void setLName(String lName) {
        this.lname = lName;
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
            throw new IllegalArgumentException("Provided email isn't valid");
        }
    }
    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return  getUserID() + "|" +
                getFname() + "|" +
                getLname() + "|" +
                getEmail() + "|" +
                getHashedPassword() + "|" +
                getRole() + "|";
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User)) return false;

        if (this == o) return true;
        return this.getEmail().equalsIgnoreCase(((User) o).getEmail());
    }
}
