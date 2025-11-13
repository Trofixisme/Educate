package com.group.educate.educate.Model.User.Company;

import com.group.educate.educate.Model.User.User;

import java.util.ArrayList;
import java.util.List;

public class Recruiter extends User {
    private String title;
    private static List<Company> companies = new ArrayList<>();
    public Recruiter(String firstName, String lastName, String email, String password, String title){
        super(firstName, lastName, email, password);
        this.title=title;
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
}
