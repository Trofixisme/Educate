//Created by Ziad on 28/10/2025

package com.group.educate.educate.Model.User.Student;

import com.group.educate.educate.Model.Job.jobPosting;
import com.group.educate.educate.Model.User.Application;
import com.group.educate.educate.Model.User.User;

public class Student extends User {

    int graduatingYear;
    String UniName;
    private String cv;

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
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
