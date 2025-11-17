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
    private final int userId;
    private UserRole role;

    //added password hashing
    public User(String fname, String lname, String email, String plainPassword,UserRole role) {
        this.userId=++counter;
        this.hashedPassword = hashPassword(plainPassword);
        setFname(fname);
        setLname(lname);
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

    public int getUserId() {
        return userId;
    }

    public String getFname() {
        return fname;
    }

    //TODO: Also add some validation when setting the user's name (Why do we even need to do that??)

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
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
