//Created by Ziad on 30/10/2025

package com.group.educate.educate.BaseModels.User;

public class CompanyEmployer extends User {

    Company company;
    String jobTitle;

    public CompanyEmployer(String password, String name, String email, String jobTitle, Company workingFor) {
        super(password, name, email, UserStatics.companyEmployer);
        company = workingFor;
        this.jobTitle = jobTitle;
    }
}
