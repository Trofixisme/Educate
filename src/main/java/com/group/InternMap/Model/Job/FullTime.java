//Made By Eyad

package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;

import java.io.Serializable;

public class FullTime extends JobPosting implements Serializable {
    private String benefits;
    private Company company;

    public FullTime(String JobDescription,  String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits,Company company) {
        super(JobDescription, JobRequirements, JobTitle, jobPostingType);
        this.benefits = benefits;
        this.company=company;
    }

    public FullTime(String fullTimeID ,String JobDescription,  String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits,Company company) {
        super(fullTimeID ,JobDescription,JobRequirements, JobTitle,jobPostingType);
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
