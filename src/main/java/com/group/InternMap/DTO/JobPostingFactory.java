package com.group.InternMap.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Job.*;

public class JobPostingFactory {
    @JsonProperty("jobPosting")
    private final JobPosting jobPosting = new JobPosting();
    private final FullTime fullTime = new FullTime();
    private final Internship internship = new Internship();
    private final FreelanceProject freelanceProject = new FreelanceProject();
    @JsonProperty("company")
    private Company company ;
    @JsonProperty("jobType")
    private String jobType;
    public JobPostingFactory() {}
    public Internship toInternship() {
        internship.setType(PostingType.Internship);
        internship.setRecruiter(jobPosting.getRecruiter());
        internship.setJobName(jobPosting.getJobName());
        internship.setJobDescription(jobPosting.getJobDescription());
        internship.setJobRequirements(jobPosting.getJobRequirements());
        internship.setCompany(company);
        return internship;
    }

    public FreelanceProject toFreelanceProject() {
        freelanceProject.setType(PostingType.FreeLanceProject);
        freelanceProject.setRecruiter(jobPosting.getRecruiter());
        freelanceProject.setJobName(jobPosting.getJobName());
        freelanceProject.setJobDescription(jobPosting.getJobDescription());
        freelanceProject.setJobRequirements(jobPosting.getJobRequirements());
        freelanceProject.setCompany(company);
        return freelanceProject;
    }

    public FullTime toFullTime() {
        fullTime.setType(PostingType.FullTime);
        fullTime.setRecruiter(jobPosting.getRecruiter());
        fullTime.setJobName(jobPosting.getJobName());
        fullTime.setJobDescription(jobPosting.getJobDescription());
        fullTime.setJobRequirements(jobPosting.getJobRequirements());
        fullTime.setCompany(company);
        return fullTime;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public FullTime getFullTime() {
        return fullTime;
    }

    public Internship getInternship() {
        return internship;
    }

    public FreelanceProject getFreelanceProject() {
        return freelanceProject;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


}