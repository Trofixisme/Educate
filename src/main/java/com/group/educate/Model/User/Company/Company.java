package com.group.educate.Model.User.Company;

import com.group.educate.Model.User.UserRole;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Company {

    private UUID companyID;

    private String industry;
    private String name;
    private URL websiteURL;
    private ArrayList<Recruiter> recruiters = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();

    @SuppressWarnings("all")
    public Company(String industry, String name, URL websiteURL, ArrayList<String> location) {
        if (companyID == null) companyID = UUID.randomUUID();
        this.industry = industry;
        this.name = name;
        this.websiteURL = websiteURL;
    }

    public Company(String companyUUID ,String industry, String name, String websiteURL, ArrayList<String> location, Recruiter... recruiters) throws MalformedURLException {
        companyID = UUID.fromString(companyUUID);
        this(industry, name, new URL(websiteURL), location);
    }
    public String getLocation() {
        return getLocation();
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
        return websiteURL.toString();
    }

    public void setWebsiteURL(String websiteURL) throws Exception {
            this.websiteURL = new URL(websiteURL);
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
                + websiteURL.toString() + '|'
                + recruiters.toString();
    }
}
