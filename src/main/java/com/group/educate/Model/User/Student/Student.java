//Created by Ziad on 28/10/2025

package com.group.educate.Model.User.Student;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.User.Application;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

public class Student extends User {

    private int graduatingYear;
    private String uniName;
    private String CV;
    private StudentMajor major;
    private StudentDepartment department;

    //students attributes
    public Student(String fName, String lName, String email,  String plainPassword, UserRole role,
                   int graduatingYear, StudentMajor major, StudentDepartment department) {

        super(fName, lName,  email, plainPassword,role);
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

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
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

    public void submitApplication(JobPosting jobPosting) {

        Application StudentApplication = new Application(this, jobPosting);
        Application.addApplication(StudentApplication);
    }
}
