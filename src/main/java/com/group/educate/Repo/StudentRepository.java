package com.group.educate.Repo;

import com.group.educate.Model.User.Student.Student;
import com.group.educate.Model.User.Student.StudentDepartment;
import com.group.educate.Model.User.Student.StudentMajor;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

import java.util.List;

public class StudentRepository extends UserRepository {

    @Override
    protected String formatUser(User student) {
        return  student.getUserID() + "|" +
                student.getFname()+ "|" +
                student.getLname()+ "|" +
                student.getEmail() + "|" +
                student.getHashedPassword() + "|" +
                student.getRole() + "|" +
                ((Student) student).getGraduatingYear()+"|"+
                ((Student) student).getMajor()+"|"+
                ((Student) student).getDepartment().getDepartmentName() + "|" ;
    }
//String fname, String lname, String email,  String plainPassword, UserRole role,
//                   //students artibutes
//                   int graduatingYear, StudentMajor major, StudentDepartment department,

    @Override
    User parseUser(String line){
        String[] parts = line.split("\\|");
        int UserId = Integer.parseInt(parts[0]);
        String Fname = parts[1] ;
        String lname = parts[2];
        String email = parts[3];
        String password = parts[4];
        String role = parts[5];
        int GraduatingYear = Integer.parseInt(parts[6]);
        String Major = parts[7];
        String Department = parts[8];


        return new Student(parts[1], parts[2], parts[3], parts[4], UserRole.STUDENT,
                Integer.parseInt(parts[6]), new StudentMajor(parts[7]), new StudentDepartment(parts[8]));
    }

    private static class RepositoryTest {

        static void main(String[] args) {
            User user = new Student("Ziad", "Ziad2", "some-email@gmail.com", "myPassword123", UserRole.STUDENT,
                    2020, new StudentMajor("Computer Science"), new StudentDepartment("Computer Science"));

            UserRepository repo = new StudentRepository();
            repo.save(user);

            List<User> users = repo.findAll();
            users.forEach(System.out::println);
        }
    }
}
