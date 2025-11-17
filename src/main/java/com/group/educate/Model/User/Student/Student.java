//Created by Ziad on 28/10/2025

package com.group.educate.Model.User.Student;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.User.Application;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

public class Student extends User {

    int graduatingYear;
    String uniName;
    private String CV;
    StudentMajor major;
    StudentDepartment department;

    public Student(String fname, String lname, String email,  String plainPassword, UserRole role,
                   //students artibutes
                   int graduatingYear, StudentMajor major, StudentDepartment department)
    {

        super( fname, lname,  email, plainPassword,role);
        this.graduatingYear = graduatingYear;
        this.major = major;
        this.department = department;
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

    public void submitApplication(JobPosting job_Posting) {

        Application StudentApplication = new Application(this, job_Posting);
        Application.addApplication(StudentApplication);
    }
}
