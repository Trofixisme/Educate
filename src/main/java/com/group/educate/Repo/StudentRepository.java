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

        String[] parts = line.split("\\|", -1);  // IMPORTANT: keep empty fields!

        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid line format (" + parts.length + " fields): " + line);
        }

        return new Student(
                parts[0],          // UUID
                parts[1],          // First name
                parts[2],          // Last name
                parts[3],          // Email
                parts[4],          // Password
                UserRole.valueOf(parts[5]),
                Integer.parseInt(parts[6]),
                parts[7],
                parts[8],
                parts[9]
        );
    }


    private static class RepositoryTest {

        static void main(String[] args) {



            UserRepository repo = new StudentRepository();


            List<User> users = repo.findAll();
            users.forEach(System.out::println);
        }
    }

}
