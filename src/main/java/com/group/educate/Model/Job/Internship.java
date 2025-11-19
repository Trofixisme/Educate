//Made By Eyad

package com.group.educate.Model.Job;

import com.group.educate.Model.User.Company.Company;

public class Internship extends JobPosting{
    private int duration;
    private Company company;

    public Internship(String JobDescription, String JobName, String JobRequirements, String JobTitle, PostingType jobPostingType,  int duration,Company company) {
        super(JobDescription, JobName, JobRequirements, JobTitle, jobPostingType);
        this.duration = duration;
        this.company=company;
    }

    public Internship(String internshipID, String jobDescription, String jobName, String jobRequirements, String jobTitle, PostingType jobPostingType, int duration, Company company) {
        super(internshipID, jobDescription, jobName, jobRequirements, jobTitle, jobPostingType);
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
