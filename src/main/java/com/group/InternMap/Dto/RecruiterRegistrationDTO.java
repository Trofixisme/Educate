package com.group.InternMap.Dto;

import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.Company.Recruiter;

public class RecruiterRegistrationDTO {
    private Recruiter user;
    private Company company;

    public Recruiter getUser() {
        return user;
    }

    public void setUser(Recruiter user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
