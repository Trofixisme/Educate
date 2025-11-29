package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Recruiter;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public  class JobPosting implements Serializable {

    private String jobDescription;
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;
    private String jobName;

    private final UUID jobPostingUUID;
    private PostingType jobPostingType;
    private String companyName;
    private Recruiter recruiter;

    public JobPosting() {
        this.jobPostingUUID = UUID.randomUUID();
    }

    public JobPosting(String jobDescription, String jobName, String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;

        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobName = jobTitle;
        jobPostingUUID = UUID.randomUUID();
        this.jobPostingType = jobPostingType;

    }

    public JobPosting(String jobPostingUUID, String jobDescription, String jobName, String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;

        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobName = jobTitle;
        this.jobPostingUUID = UUID.fromString(jobPostingUUID);
        this.jobPostingType = jobPostingType;

    }

    public Date getDatePosted() {
        return datePosted;
    }

    public String getJobDescription() {
        return jobDescription;
    }


    public String getJobRequirements() {
        return jobRequirements;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobPostingID() {
        return jobPostingUUID.toString();
    }


    public PostingType getJobPostingType() {
        return jobPostingType;
    }

    public void setJobPostingType(PostingType jobPostingType) {
        this.jobPostingType = jobPostingType;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public String getRecruiterEmail() {
        if (recruiter == null) {
            return "null";
        }
        return recruiter.getEmail();
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public String getCompanyName() {return  companyName;}


    @Override
    public String toString() {
        return jobDescription + '|'

                + datePosted.toString() + '|'
                + jobRequirements + '|'
                + jobName + '|'
                + getJobPostingID() + '|'
                + jobPostingType + '|';
    }
}
