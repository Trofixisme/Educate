package com.group.InternMap.Company;

import com.group.InternMap.Recruiter.Recruiter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String industry;

    @Column(nullable = false, unique = true)
    private String name;
    private String websiteURL;
    private String locationOfHQ;
    @Column(nullable = true)
    private String logo;

    @ManyToMany(mappedBy = "companies")
    private List<Recruiter> recruiters = new ArrayList<>();
    public Company() {}

    public String getLocationOfHQ() {
        return locationOfHQ;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        //TODO: Also add validation
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setLocationOfHQ(String locationOfHQ) {
        this.locationOfHQ = locationOfHQ;
    }

    public void setName(String name) {
        //TODO: Add validation
        this.name = name;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        //TODO: Add validation here
            if (websiteURL == null || websiteURL.isBlank()) return;
            this.websiteURL = websiteURL;
    }

// public void addRecruiter(Recruiter recruiter) {
//     if (recruiter == null) return;
//     if (!recruiters.contains(recruiter)) recruiters.add(recruiter);
// }

// public void removeRecruiter(Recruiter recruiter) {
//     recruiters.remove(recruiter);
// }

}