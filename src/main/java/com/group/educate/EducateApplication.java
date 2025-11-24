package com.group.educate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class EducateApplication {

    public static void main(String[] args) throws IllegalClassFormatException {

        SpringApplication.run(EducateApplication.class, args);
    }
}
