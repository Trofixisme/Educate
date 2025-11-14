//Made By Eyad

package com.group.educate.educate.Model.Job;
import java.lang.instrument.IllegalClassFormatException;
import java.time.Instant;
import java.util.Date;
public class JobPosting {
    private String JobDescription;
    private String JobName;
    private Date DatePosted = Date.from(Instant.now());
    private String JobLocation;
    private String JobRequirements;
    private String JobTitle;
    private final int JobPostingID;
    private PostingType jobPostingType;
    private static int counter=0;


    public JobPosting(String JobDescription, String JobName, String JobLocation, String JobRequirements, String JobTitle, PostingType jobPostingType) {
        this.JobDescription =JobDescription;
        this.JobName = JobName;
        this.DatePosted = new Date();
        this.JobLocation = JobLocation;
        this.JobRequirements = JobRequirements;
        this.JobTitle = JobTitle;
        this.JobPostingID =++counter;
        this.jobPostingType = jobPostingType;

    }

    public String getJobLocation() {
        return JobLocation;
    }

    public void setJobLocation(String jobLocation) {
        JobLocation = jobLocation;
    }

    public Date getDatePosted() {
        return DatePosted;
    }


    public String getJobDescription() {
        return JobDescription;
    }

    public String getJobName() {
        return JobName;
    }

    public String getJobRequirements() {
        return JobRequirements;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public int getJobPostingID() {
        return JobPostingID;
    }

    public PostingType getJobType() {
        return jobPostingType;
    }

}
