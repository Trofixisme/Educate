//Made By Eyad

package com.group.educate.Model.Job;

import com.group.educate.Model.User.Company.Company;

import java.io.Serializable;

public class FullTime extends JobPosting implements Serializable {
    private String benefits;
    private Company company;

    public FullTime(String JobDescription, String JobName, String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits,Company company) {
        super(JobDescription, JobName, JobRequirements, JobTitle, jobPostingType);
        this.benefits = benefits;
        this.company=company;
    }

    public FullTime(String fullTimeID ,String JobDescription, String JobName, String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits,Company company) {
        super(fullTimeID ,JobDescription, JobName, JobRequirements, JobTitle,jobPostingType);
        this.benefits = benefits;
        this.company=company;
    }

    public String getCompanyLocation(){
        return company.getLocation();
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    @Override
    public String toString() {
        return super.toString()
                + benefits + '|'
                + company.toString() + '|';
    }
}
