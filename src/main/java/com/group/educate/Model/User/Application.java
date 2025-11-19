package com.group.educate.Model.User;

import com.group.educate.Model.Job.JobPosting;

import java.util.ArrayList;
import java.util.UUID;


public class Application {

    private final UUID applicationID = UUID.randomUUID();
    JobPosting jobPosting;
    Student student;
    private static final ArrayList<Application> allApplications = new ArrayList<>();

    public Application(Student student, JobPosting jobPosting) {
        this.jobPosting = jobPosting;
        this.student = student;
    }

    public String getApplicationID() {
        return applicationID.toString();
    }

    public static ArrayList<Application> getAllApplications() {
        return allApplications;
    }

    public static void addApplication(Application application) {
        Application.allApplications.add(application);
    }


}