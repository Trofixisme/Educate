//Created by Ziad on 28/10/2025

package com.group.InternMap.Model.User;

import com.group.InternMap.Model.Job.JobPosting;


public class Student extends User {
    private int graduatingYear;
    private String uniName;
    private String studentMajor;
    private String faculty;
    private CV cv;
    public Student(){
        super();
        this.role = UserRole.STUDENT;
    }

    //students attributes
    public Student(String fName, String lName, String email,  String plainPassword, UserRole role,
                   int graduatingYear, String uniName, String studentMajor, String department) {

        super(fName, lName,  email, plainPassword,role);
        this.graduatingYear = graduatingYear;
        this.uniName = uniName;
        this.studentMajor = studentMajor;
        this.faculty = department;
    }

    public Student(String UUID, String fName, String lName, String email,  String plainPassword, UserRole role,
                   int graduatingYear, String uniName, String studentMajor, String department) {
        super(UUID ,fName, lName,  email, plainPassword,role);
        this.graduatingYear = graduatingYear;
        this.uniName = uniName;
        this.studentMajor = studentMajor;
        this.faculty = department;
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

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Application submitApplication(JobPosting jobPosting) {
        return new Application(this, jobPosting);
    }
    public void createCV() {
        new CV();
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return super.toString() + '|'
                + graduatingYear + '|'
                + uniName + '|'
                + studentMajor + '|'
                + faculty + '|';
    }
}