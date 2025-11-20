package com.group.educate.Repo;

import com.group.educate.Model.Roadmap.Skill.Skill;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    protected static void write(String fileName) throws Exception {
        FileOutputStream stream = new FileOutputStream(fileName);
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);

        objectStream.writeObject(new Skill("Swift Student Challenge", "Empty", new ArrayList<>(List.of(new URL[]{new URL("https://developer.apple.com")}))));
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
