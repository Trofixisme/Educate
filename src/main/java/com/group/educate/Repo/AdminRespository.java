package com.group.educate.Repo;

import com.group.educate.Model.User.Admin;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

public class AdminRespository extends UserRepository {
    private String formatUser(Admin admin) {
        return  admin.getUserId() + "|" +
                admin.getFname()+ "|" +
                admin.getLname()+ "|" +
                admin.getEmail() + "|" +
                admin.getHashedPassword() + "|" +
                admin.getRole() + "|" ;

    }


    @Override
    User parseUser(String line) {
        String[] parts=line.split("\\|");
        int UserId=Integer.parseInt(parts[0]);
        String Fname=parts[1] ;
        String lname=parts[2];
        String email=parts[3];
        String password=parts[4];
        String role=parts[5];

        return new Admin(parts[1],parts[2], parts[3], parts[4], UserRole.ADMIN);
    }
}
