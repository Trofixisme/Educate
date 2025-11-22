package com.group.educate.Model.Job;

import com.group.educate.Model.User.Company.Company;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JobPosting implements Serializable {

    private String jobDescription;
    private String jobName;
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;
    private String jobTitle;

    private final UUID jobPostingUUID;
    private PostingType jobPostingType;

    public JobPosting(String jobDescription, String jobName, String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;
        this.jobName = jobName;
        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobTitle = jobTitle;
        jobPostingUUID = UUID.randomUUID();
        this.jobPostingType = jobPostingType;


    }

    public JobPosting(String jobPostingUUID, String jobDescription, String jobName, String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;
        this.jobName = jobName;
        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobTitle = jobTitle;
        this.jobPostingUUID = UUID.fromString(jobPostingUUID);
        this.jobPostingType = jobPostingType;

    }

    public Date getDatePosted() {
        return datePosted;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobPostingID() {
        return jobPostingUUID.toString();
    }

    public PostingType getJobType() {
        return jobPostingType;
    }

    @Override
    public String toString() {
        return jobDescription + '|'
                + jobName + '|'
                + datePosted.toString() + '|'
                + jobRequirements + '|'
                + jobTitle + '|'
                + getJobPostingID() + '|'
                + jobPostingType + '|';
    }
}
