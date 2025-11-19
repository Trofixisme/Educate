package com.group.educate.Model.User.Company;

import com.group.educate.Model.User.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String industry;
    private String name;
    //type of id is not decided
    private String websiteURL;
    private UserRole role;
    private static List<Recruiter> recruiters = new ArrayList<>();

    public Company(String industry, String name, String websiteURL, UserRole role) {
        this.industry = industry;
        this.name = name;
        this.websiteURL = websiteURL;
        this.role = role;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }


    public static void setRecruiters(List<Recruiter> recruiters) {
        Company.recruiters = recruiters;
    }

    public UserRole getRole() {
        return role;
    }

    public static void addRecruiter(Recruiter recruiter) {
        Company.recruiters.add(recruiter);
    }
    public static void removeRecruiter(Recruiter recruiter) {
        Company.recruiters.remove(recruiter);
    }
}
