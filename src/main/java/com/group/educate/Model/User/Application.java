package com.group.educate.Model.User;

import com.group.educate.Model.Job.JobPosting;

import java.io.Serializable;
import java.util.UUID;

public class Application implements Serializable {

    private final UUID applicationID = UUID.randomUUID();

    JobPosting jobPosting;
    Student student;

    public Application(Student student, JobPosting jobPosting) {
        this.jobPosting = jobPosting;
        this.student = student;
    }

    public UUID getApplicationID() {
        return applicationID;
    }

    @Override
    public String toString() {
        return  applicationID + "|" +
                jobPosting + "|" +
                student + "|";
    }
}