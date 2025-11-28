package com.group.InternMap.Repo;

import com.group.InternMap.Model.Roadmap.Skill.Skill;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.UserRole;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class FileManager {

    protected static <T> void write(String fileName, T... objects) throws Exception {
        FileOutputStream stream = new FileOutputStream(fileName);
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);

        if (objects != null) {
            objectStream.writeObject(new ArrayList<T>(List.of(objects)));
        } else {
            objectStream.writeObject(null);
        }
    }

    protected static <T> ArrayList<T> read(Class<T> type, String fileName) throws Exception {
        FileInputStream stream = new FileInputStream(fileName);
        ObjectInputStream objectStream = new ObjectInputStream(stream);

        ArrayList<T> returningList = (ArrayList<T>) objectStream.readObject();
        if (returningList == null) {
            return new ArrayList<T>();
        } else {
            return returningList;
        }
    }

    private static final class RepositoryTest {
        static void main() throws Exception {

            String filePath = "data/test.txt";

            write(filePath, new Skill("Swift Student Challenge", "Empty", new ArrayList<>(List.of(new URL[]{new URL("https://developer.apple.com")}))),
                    new Skill("Swift Student Challenge", "Empty", new ArrayList<>(List.of(new URL[]{new URL("https://www.apple.com/iphone-17/")}))));
            read(Skill.class,filePath).get(0);


                write(filePath,
                        new Student("Ziad", "Ali", "ziad@example.com", "password123", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI"),
                        new Student("Alice", "Smith", "alice@example.com", "securePass", UserRole.STUDENT, 2025, "MIT", "Software Engineering", "Engineering"),
                        new Student("Bob", "Jones", "bob@example.com", "bobPass", UserRole.STUDENT, 2023, "Stanford", "Data Science", "Mathematics")
                );
            System.out.println(read(Skill.class, filePath));
            }
        }

    }
