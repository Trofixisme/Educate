//Made By Eyad

package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;

import java.io.Serializable;

public class Internship extends JobPosting implements Serializable {
    private int duration;
    private Company company;

    public Internship(String JobDescription,String JobRequirements, String JobTitle, PostingType jobPostingType,  int duration,Company company) {
        super(JobDescription, JobRequirements, JobTitle, jobPostingType);
        this.duration = duration;
        this.company=company;
    }

    public Internship(String internshipID, String jobDescription,  String jobRequirements, String jobTitle, PostingType jobPostingType, int duration, Company company) {
        super(internshipID, jobDescription,jobRequirements, jobTitle, jobPostingType);
        this.duration = duration;
        this.company=company;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return super.toString()
                + duration + '|'
                + company.toString() + '|';
    }
}
