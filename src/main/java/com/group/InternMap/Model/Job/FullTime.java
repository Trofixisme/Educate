package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;
import java.io.Serializable;

@SuppressWarnings("unused")
public class FullTime  implements Serializable {
    private String benefits;
    private Company company;

    public FullTime() {
    }

    public FullTime(String JobDescription, String JobRequirements, String JobTitle, PostingType jobPostingType, String benefits, Company company) {
        this.benefits = benefits;
        this.company=new Company();
    }

    public FullTime(String fullTimeID ,String JobDescription,  String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits,Company company) {

        this.benefits = benefits;
        this.company=company;
    }


    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    @Override
    public String toString() {
        return super.toString()
                + benefits + '|'
                + company.toString() + '|';
    }
}