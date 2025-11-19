package com.group.educate.Model.User.Company;

import com.group.educate.Model.User.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private String industry;
    private String name;
    //type of id is not decided
    //Update: we decided on UUID
    private String websiteURL;
    private UserRole role;
    private List<Recruiter> recruiters = new ArrayList<>();

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

    public UserRole getRole() {
        return role;
    }

    public void addRecruiter(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void removeRecruiter(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    @Override
    public String toString() {
        return name + '|'
                + industry + '|'
                + websiteURL + '|'
                + role + '|'
                + recruiters.toString();
    }
}
