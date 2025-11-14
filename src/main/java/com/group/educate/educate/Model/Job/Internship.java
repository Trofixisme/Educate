//Made By Eyad

package com.group.educate.educate.Model.Job;

public class Internship extends JobPosting{
    private int duration;


    public Internship(String JobDescription, String JobName, String JobLocation, String JobRequirements, String JobTitle, PostingType jobPostingType,int duration) {
        super(JobDescription, JobName, JobLocation,JobRequirements, JobTitle,jobPostingType);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
