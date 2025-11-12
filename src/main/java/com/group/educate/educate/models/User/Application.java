package com.group.educate.educate.models.User;

import com.group.educate.educate.models.job.jobPosting;


public class Application {

    private static int counter;
    private final int ApplicationId;
    jobPosting jobPosting;
    Student student;
    public Application() {
        this.ApplicationId=++counter;
    }

}
