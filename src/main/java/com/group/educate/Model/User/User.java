//Created by Ziad on 28/10/2025

package com.group.educate.Model.User;

import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private String plainPassword;
    private String fname;
    private String lname;
    private String email;
    private final UUID userID;
    private final UserRole role;
    public User(){
        userID = UUID.randomUUID();
        this.role = UserRole.STUDENT;
    }

    public User(String fname, String lname, String email, String plainPassword, UserRole role) {
        userID = UUID.randomUUID();
        hashPassword(plainPassword);
        setFname(fname);
        setLname(lname);
        setEmail(email);
        this.role = role;
    }

    public User(String userID, String fname, String lname, String email, String plainPassword, UserRole role) {
        this.userID = UUID.fromString(userID);
        hashPassword(plainPassword);
        setFname(fname);
        setLname(lname);
        setEmail(email);
        this.role = role;
    }

    //Hash password with BCrypt
    public void hashPassword(String plainPassword) { this.plainPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); }

    //Check if the input password matches the stored hash
    public boolean verifyPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.plainPassword);
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
       hashPassword(plainPassword);
    }

    public String getFname() {
        return fname;
    }

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

    public UUID getUserID() {
        return userID;
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
                getPlainPassword() + "|" +
                getRole() + "|";
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User)) return false;

        if (this == o) return true;
        return this.getEmail().equalsIgnoreCase(((User) o).getEmail());
    }
}
