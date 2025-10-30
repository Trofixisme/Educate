//Created by Ziad on 29/10/2025

package com.group.educate.educate.BaseModels.User;

import java.util.ArrayList;

public class Company extends User {

    String websiteURL;
    String Description;
    String industry;
    ArrayList<CompanyEmployer> companyEmployers = new ArrayList<>();

    public Company(String password, String name, String email) {
        super(password, name, email, UserStatics.company);
    }
}
