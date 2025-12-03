package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Recruiter;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class JobPosting implements Serializable {

    private String jobDescription;
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;
    private String jobName;

    private final UUID jobPostingUUID;
    private PostingType jobPostingType;
    private String companyName;
    private Recruiter recruiter;
    private ArrayList<Application> application;

    public JobPosting() {
        this.jobPostingUUID = UUID.randomUUID();
    }

    public JobPosting(String jobDescription,  String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;

        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobName = jobTitle;
        jobPostingUUID = UUID.randomUUID();
        this.jobPostingType = jobPostingType;

    }

    public JobPosting(String jobPostingUUID, String jobDescription,  String jobRequirements, String jobTitle, PostingType jobPostingType) {
        this.jobDescription = jobDescription;
        this.datePosted = new Date();
        this.jobRequirements = jobRequirements;
        this.jobName = jobTitle;
        this.jobPostingUUID = UUID.fromString(jobPostingUUID);
        this.jobPostingType = jobPostingType;

    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
    public void setRecruiterEmail(String recruiterEmail) {
        if (recruiter == null) {
            recruiter.setEmail(recruiterEmail);
        }
    }

    public UUID getJobPostingUUID() {
        return jobPostingUUID;
    }

    public void setApplication(Application application) {
        this.application.add(application);
    }

    public ArrayList<Application> viewApplications() {
       return application;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public String getCompanyName() {return  companyName;}
    public ArrayList<Application> getApplication() {
        return application;
    }

    public void deleteApplication(Application application) {
        this.application.remove(application);
    }

    @Override
    public String toString() {
        return jobDescription + '|'

                + datePosted.toString() + '|'
                + jobRequirements + '|'
                + jobName + '|'
                + getJobPostingUUID() + '|'
                + jobPostingType + '|';
    }
}
