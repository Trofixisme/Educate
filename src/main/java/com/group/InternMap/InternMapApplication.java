package com.group.InternMap;

import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Repo.ShutDownSaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {
        ShutDownSaver.registerShutdownHook();   // REGISTER IT HERE
        SpringApplication.run(InternMapApplication.class, args);
    }
}
