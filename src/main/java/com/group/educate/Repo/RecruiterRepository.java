package com.group.educate.Repo;

import com.group.educate.Model.User.Company.Company;
import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

import java.util.stream.Collectors;

public class RecruiterRepository extends UserRepository {


    private String formatRecruiter(Recruiter recruiter) {
        return recruiter.getFname()+ "|" +
                recruiter.getLname()+ "|" +
               recruiter.getEmail() + "|" +
                recruiter.getHashedPassword() + "|" +
                recruiter.getRole() + "|" +
                recruiter.getTitle()+ "|" +
               // recruiter.getCompanies()+ "|";
                //this is the only way to get the companies details as Strings
                recruiter.getCompanies().stream() .map(Company::getName)
                        .collect(Collectors.joining(","));
    }
    @Override
    public void save(User user) {
        String line= formatRecruiter((Recruiter)user);
        //getter to the user path ? create different path?
        writeToFile("data/users.txt",line,true);
    }
    @Override
    User parseUser(String line) {
        String[] parts = line.split("\\|");
        int UserId=Integer.parseInt(parts[0]);
        String Fname=parts[1] ;
        String lname=parts[2];
        String email=parts[3];
        String password=parts[4];
        String role=parts[5];
        String title=parts[6];
        return new Recruiter(parts[1],parts[2],parts[3],parts[4], UserRole.EMPLOYER,parts[6]);
    }
}
