package com.group.educate.Model.User.Company;

import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Recruiter extends User {
    private String title;
    private static List<Company> companies = new ArrayList<>();
    public Recruiter(String fName, String lName, String email, String plainPassword, UserRole role){
        super(fName, lName,  email, plainPassword,role);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<Company> getCompanies() {
        return companies;
    }

    public static void setCompanies(List<Company> companies) {
        Recruiter.companies = companies;
    }
    public void addCompany(Company company) {
        companies.add(company);
    }
    public void removeCompany(Company company) {
        companies.remove(company);
    }
}
