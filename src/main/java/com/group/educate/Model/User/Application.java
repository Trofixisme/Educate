package com.group.educate.Model.User;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.User.Student.Student;

import java.util.ArrayList;
import java.util.List;


public class Application {

    private static int counter;
    private final int ApplicationID;
    JobPosting jobPosting;
    Student student;
    private static final ArrayList<Application> allApplications = new ArrayList<>();

    //public Application() {this.ApplicationId=++counter;}

    public Application(Student student, JobPosting jobPosting) {
        this.jobPosting = jobPosting;
        this.student = student;
        this.ApplicationID = ++counter;
    }

    public int getApplicationID() {
        return ApplicationID;
    }

    public static ArrayList<Application> getAllApplications() {
        return allApplications;
    }

    public static int getCounter() {
        return counter;
    }

    public static void addApplication(Application application) {
        Application.allApplications.add(application);
    }

}