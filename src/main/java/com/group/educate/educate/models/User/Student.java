//Created by Ziad on 28/10/2025

package com.group.educate.educate.models.User;

import com.group.educate.educate.models.job.jobPosting;

public class Student extends User {

    int graduatingYear;
    String UniName;

    public Student(String fname,String lname,String plainPassword, String email) {
        super(fname,lname, plainPassword, email);
    }

    public int getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(int graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public String getUniName() {
        return UniName;
    }

    public void setUniName(String uniName) {
        UniName = uniName;
    }

    public void submitApplication(jobPosting job_Posting) {
        Application StudentApplication = new Application(job_Posting, this);
        Application.addApplication(StudentApplication);
    }
}
