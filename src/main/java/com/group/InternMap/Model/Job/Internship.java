package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Internship  implements Serializable {
    private int duration;
    private Company company;
    public Internship() {

    }

    public Internship(String JobDescription,String JobRequirements, String JobTitle, PostingType jobPostingType,  int duration,Company company) {

        this.duration = duration;
        this.company=new Company();
    }

    public Internship(String internshipID, String jobDescription,  String jobRequirements, String jobTitle, PostingType jobPostingType, int duration, Company company) {
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