//Created by Ziad on 28/10/2025

package com.group.educate.Model.User;

import com.group.educate.Model.Job.JobPosting;
import java.io.Serializable;


public class Student extends User implements Serializable {

    private int graduatingYear;
    private String uniName;
    private String studentMajor;
    private String department;

    //students attributes
    public Student(String fName, String lName, String email,  String plainPassword, UserRole role,
                   int graduatingYear, String uniName, String studentMajor, String department) {

        super(fName, lName,  email, plainPassword,role);
        this.graduatingYear = graduatingYear;
        this.uniName = uniName;
        this.studentMajor = studentMajor;
        this.department = department;
    }

    public Student(String UUID, String fName, String lName, String email,  String plainPassword, UserRole role,
                   int graduatingYear, String uniName, String studentMajor, String department) {
        super(UUID ,fName, lName,  email, plainPassword,role);
        this.graduatingYear = graduatingYear;
        this.uniName = uniName;
        this.studentMajor = studentMajor;
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

    public String getMajor() {
        return studentMajor;
    }

    public void setMajor(String major) {
        studentMajor = major;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void submitApplication(JobPosting jobPosting) {

        Application StudentApplication = new Application(this, jobPosting);
    }

    @Override
    public String toString() {
        return super.toString() + '|'
                + graduatingYear + '|'
                + uniName + '|'
                + studentMajor + '|'
                + department + '|';
    }
}
