package com.group.educate.Model.User;

import com.group.educate.Model.Job.JobPosting;

import java.util.ArrayList;
import java.util.UUID;

public class Application {

    private final UUID applicationID = UUID.randomUUID();

    JobPosting jobPosting;
    Student student;

    public Application(Student student, JobPosting jobPosting) {
        this.jobPosting = jobPosting;
        this.student = student;
    }

    @Override
    public String toString() {
        return  applicationID + "|" +
                jobPosting + "|" +
                student + "|";
    }
}