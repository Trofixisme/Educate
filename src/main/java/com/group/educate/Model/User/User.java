//Created by Ziad on 28/10/2025

package com.group.educate.Model.User;
import org.mindrot.jbcrypt.BCrypt;
public abstract class User {

    //TODO: Somehow resolve the issue causing the various objects extending this class to have unique IDs when they shouldn't
    //Update: Issue might've been resolved. No testing has been conducted yet
    //update made the id as an integer

    private static int counter = 0;
    private String hashedPassword;
    private String fName;
    private String lName;
    private String email;
    private final int userID;
    private final UserRole role;

    //added password hashing
    public User(String fName, String lName, String email, String plainPassword, UserRole role) {
        this.userID =++counter;
        this.hashedPassword = hashPassword(plainPassword);
        setFName(fName);
        setLName(lName);
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

    public String getFName() {
        return fName;
    }

    //TODO: Also add some validation when setting the user's name (Why do we even need to do that??)

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
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
}
