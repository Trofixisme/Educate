//Created by Ziad on 28/10/2025

package com.group.educate.educate.Model.User.Student;

import com.group.educate.educate.Model.Job.jobPosting;
import com.group.educate.educate.Model.User.Application;
import com.group.educate.educate.Model.User.User;

public class Student extends User {

    int graduatingYear;
    String uniName;
    private String CV;
    StudentMajor major;
    StudentDepartment department;

    public Student(String firstName, String lastName, String email, String password,
                   //students artibutes
                   int graduatingYear, StudentMajor major, StudentDepartment department ,String cv)
    {

        super(firstName, lastName, email, password);
        this.graduatingYear = graduatingYear;
        this.major = major;
        this.department = department;
        this.CV = cv;
    }

    public int getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(int graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getCv() {
        return CV;
    }

    public void setCv(String cv) {
        this.CV = cv;
    }

    public StudentMajor getMajor() {
        return major;
    }

    public void setMajor(StudentMajor major) {
        this.major = major;
    }

    public StudentDepartment getDepartment() {
        return department;
    }

    public void setDepartment(StudentDepartment department) {
        this.department = department;
    }

    public void submitApplication(jobPosting job_Posting) {
        Application StudentApplication = new Application(job_Posting, this);
        Application.addApplication(StudentApplication);
    }
}
