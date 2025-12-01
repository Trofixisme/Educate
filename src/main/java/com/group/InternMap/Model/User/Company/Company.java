package com.group.InternMap.Model.User.Company;

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
//        this.name = "Unnamed Company";
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
        this(industry, name, websiteURL, location); // must be first
        this.companyID = UUID.fromString(companyUUID); // assign after
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

    public void setCompanyID(UUID companyID) {
        this.companyID = companyID;
    }

    public void setWebsiteURL(String websiteURL) {
            if (websiteURL == null || websiteURL.isBlank()) return;
            this.websiteURL = websiteURL;
    }

    public UUID getCompanyID() {
        return companyID;
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
        return "Company{" +
                "id=" + companyID +
                ", name='" + name + '\'' +
                ", recruiterCount=" + recruiters.size() + // Just print count, not the list
                '}';
    }


    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Company)) return false;
        Company other = (Company) o;
        // Null-safe version
        if (this.name == null && other.name == null) return true;
        if (this.name == null || other.name == null) return false;
        return name.equalsIgnoreCase(other.name);
    }


}
