package com.group.educate.Repo;

import com.group.educate.Model.User.Student;

import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

import java.util.List;

public class StudentRepository extends UserRepository {

    @Override
    protected String formatUser(User student) {
        return student.toString();

    }
//String fname, String lname, String email,  String plainPassword, UserRole role,
//                   //students attributes
//                   int graduatingYear, StudentMajor major, StudentDepartment department,

    @Override
    User parseUser(String line){
        String[] parts = line.split("\\|");
        String userId=parts[0];
        String Fname = parts[1];
        String lname = parts[2];
        String email = parts[3];
        String password = parts[4];
        String role = parts[5];

        try {
            int GraduatingYear = Integer.parseInt(parts[6]);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
        String uniName=parts[7];
        String Major = parts[8];
        String Department = parts[9];

        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], UserRole.STUDENT,
                Integer.parseInt(parts[6]), parts[7], parts[8],parts[9]);

    }

    private static class RepositoryTest {

        static void main(String[] args) {



            UserRepository repo = new StudentRepository();


            List<User> users = repo.findAll();
            users.forEach(System.out::println);
        }
    }

}
