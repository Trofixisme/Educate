package com.group.educate.educate.Model.User.Company;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String industry;
    private String name;
    // type of id is not decided
    private String websiteURL;
    private static List<Recruiter> recruiters = new ArrayList<>();

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

    public static List<Recruiter> getRecruiters() {
        return recruiters;
    }

    public static void setRecruiters(List<Recruiter> recruiters) {
        Company.recruiters = recruiters;
    }
}
