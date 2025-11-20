package com.group.educate.Repo;

import com.group.educate.Model.Roadmap.Skill.Skill;
import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.UserRole;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

    protected static void write(String fileName) throws Exception {
        FileOutputStream stream = new FileOutputStream(fileName);
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);
        objectStream.writeObject(new Skill("Hello", "Hi", null));

    }

    protected static void read(String fileName) throws Exception {
        FileInputStream stream = new FileInputStream(fileName);
        ObjectInputStream objectStream = new ObjectInputStream(stream);
        Skill skill = (Skill) objectStream.readObject();
        System.out.println(skill);
    }

    private static final class RepositoryTest {
        static void main() throws Exception {
            write("Application.txt");
            read("Application.txt");
        }
    }

}
