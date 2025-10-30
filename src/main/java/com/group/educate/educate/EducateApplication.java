package com.group.educate.educate;

import com.group.educate.educate.BaseModels.User.Employer;
import com.group.educate.educate.BaseModels.User.Student;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class EducateApplication {

    public static void main(String[] args) throws IllegalClassFormatException {

//        SpringApplication.run(EducateApplication.class, args);

        Student student = new Student("Hey", "Ziad", "REDACTED@.");
        Employer employer = new Employer("Hi", "Employer1", "REDACTED@.");

        System.out.println(student.getUserID());
        System.out.println(employer.getUserID());
    }

}
