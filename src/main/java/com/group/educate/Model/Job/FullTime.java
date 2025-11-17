//Made By Eyad

package com.group.educate.Model.Job;

public class FullTime extends JobPosting{
    private String benefits;

    public FullTime(String JobDescription, String JobName, String JobLocation, String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits) {
        super(JobDescription, JobName, JobLocation,JobRequirements, JobTitle,jobPostingType);
        this.benefits = benefits;
    }

    public String getBenefits() {
        return benefits;
    }
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}
