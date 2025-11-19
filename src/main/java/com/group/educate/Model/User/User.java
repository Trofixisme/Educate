//Created by Ziad on 28/10/2025

package com.group.educate.Model.User;
import org.mindrot.jbcrypt.BCrypt;
public abstract class User {

    //TODO: Somehow resolve the issue causing the various objects extending this class to have unique IDs when they shouldn't
    //Update: Issue might've been resolved. No testing has been conducted yet
    //update made the id as an integer

    private static int counter = 0;
    private String hashedPassword;
    private String fname;
    private String lname;
    private String email;
    private final int userID;
    private final UserRole role;

    //added password hashing
    public User(String fname, String lname, String email, String plainPassword, UserRole role) {
        this.userID =++counter;
        this.hashedPassword = hashPassword(plainPassword);
        setFName(fname);
        setLName(lname);
        setEmail(email);
        this.role=role;
    }
    // Hash password with BCrypt
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Check if the input password matches the stored hash
    public boolean verifyPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.hashedPassword);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getUserID() {
        return userID;
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
        return "User{" +
                "hashedPassword='" + hashedPassword + '\'' +
                ", fName='" + fname + '\'' +
                ", lName='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", userID=" + userID +
                ", role=" + role +
                '}';
    }
}
