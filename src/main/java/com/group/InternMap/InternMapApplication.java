package com.group.InternMap;

import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Repo.ShutDownSaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationShutdownHandlers;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.IllegalClassFormatException;


import static com.group.InternMap.Repo.RepositoryAccessors.allRoadmaps;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {// REGISTER IT HERE
        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
        ShutDownSaver.registerShutdownHook();
    }
}
