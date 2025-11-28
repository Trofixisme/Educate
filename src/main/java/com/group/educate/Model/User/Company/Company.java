package com.group.educate.Model.User.Company;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

public class Company implements Serializable {

    private UUID companyID;

    private String industry;
    private String name;
    private String websiteURL = "";
    private ArrayList<Recruiter> recruiters = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();

    public Company() {
        companyID = UUID.randomUUID();
    }

    @SuppressWarnings("all")
    public Company(String industry, String name, String websiteURL,
                   ArrayList<String> location) {
        if (companyID == null) companyID = UUID.randomUUID();
        this.industry = industry;
        this.name = name;
        this.websiteURL = websiteURL;
    }

    public Company(String companyUUID ,String industry, String name, String websiteURL,
                   ArrayList<String> location, Recruiter... recruiters) throws MalformedURLException {
        companyID = UUID.fromString(companyUUID);
        this(industry, name, websiteURL, location);
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
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
            if (websiteURL == null || websiteURL.isBlank()) return;
            this.websiteURL = websiteURL;
    }

    public String getCompanyID() {
        return companyID.toString();
    }

    public void addRecruiter(Recruiter recruiter) {
        if (recruiter == null) return;
        if (!recruiters.contains(recruiter)) recruiters.add(recruiter);
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Company company)) return false;

        return name.equalsIgnoreCase(((Company) o).name);
    }
}
