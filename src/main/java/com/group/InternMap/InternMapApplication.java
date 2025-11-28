package com.group.InternMap;

import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {

        SpringApplication.run(InternMapApplication.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(RepositoryAccessors::saveAll));
    }
}
