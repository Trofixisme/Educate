package com.group.educate.educate.Model.User;

import com.group.educate.educate.Model.Job.jobPosting;

public class Application {
    private static int counter;
    private final int ApplicationId;
    jobPosting jobPosting;
    Student student;
    public Application() {
        this.ApplicationId=++counter;

    }
}
