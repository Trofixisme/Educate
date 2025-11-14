//Made By Eyad

package com.group.educate.educate.Model.Job;

public class Internship extends JobPosting{
    private int Duration;


    public Internship(Type JobType) {
        super(JobType);
        this.Duration = 0;
    }

    public int getDuration() {
        return Duration;
    }
}
