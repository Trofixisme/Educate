package com.group.InternMap.Student;

import com.group.InternMap.User.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

//crud operations
//create,read,update,delete
@Service
public class StudentService extends UserService {

    StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public void updateStudent(Student studentToUpdate, Student studentToUpdateWith) {
        if (isEmailValid(studentToUpdateWith.getEmail())) {
            studentToUpdate.setEmail(studentToUpdateWith.getEmail());
        }

        studentToUpdate.setStudentMajor(studentToUpdateWith.getStudentMajor());
        studentToUpdate.setFaculty(studentToUpdateWith.getFaculty());
        studentToUpdate.setUniName(studentToUpdateWith.getUniName());
        studentToUpdate.setGraduatingYear(studentToUpdateWith.getGraduatingYear());
        studentToUpdate.setProfilePicture(studentToUpdateWith.getProfilePicture());

        try {
            studentRepo.save(studentToUpdate);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Could not upsdate profile because a user with this email already exists.");
        }
    }
}