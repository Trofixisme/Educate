package com.group.InternMap.Model.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class Application implements Serializable,Comparable<Application> {

    private UUID applicationID = UUID.randomUUID();
    private String fname;
    private String lname;
    private String email;
    private String phoneNumber;
    private CV cv;
    private Date applicationDate=Date.from(Instant.now());;

    public Application() {
        this.applicationID = UUID.randomUUID();
        this.applicationDate = new Date(); // Fix
    }

    public Application(String fName, String lName, String email, String phoneNumber,CV cv) {
        this.fname = fName;
        this.lname = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cv =cv;
        this.applicationDate = new Date();
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationID() {
        return applicationID.toString();
    }

    public void setApplicationID(UUID applicationID) {
        this.applicationID = applicationID;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Application application) {
        if(this.getApplicationDate().before(application.getApplicationDate())){
            return 1;
        }
        else if(this.getApplicationDate().after(application.getApplicationDate())){
            return -1;
        }
        return 0;
    }
}