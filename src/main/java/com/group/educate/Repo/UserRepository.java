package com.group.educate.Repo;
import java.io.BufferedWriter;

import java.io.*;
import java.util.*;
import com.group.educate.Model.User.User;

public abstract class UserRepository {
    private final String path = "data/users.txt";

    //Constructor to create a data directory if it doesn't exist
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public UserRepository(){
        File file = new File("data");
        if(!file.exists()) file.mkdirs();
    }

    public void save(User user){
        String line=formatUser(user);
        writeToFile(path,line,true);
    }
    // Convert User → text
    private String formatUser(User user) {
        return  user.getUserId() + "|" +
                user.getFname()+ "|" +
                user.getLname()+ "|" +
                user.getEmail() + "|" +
                user.getRole() + "|" ;
    }

    //Convert User → text
//    protected String formatUser(User user) {
//        return user.getUserId() + "|" +
//                user.getFname()+ "|" +
//                user.getLname()+ "|" +
//                user.getEmail() + "|" +
//                user.getRole() + "|" ;
//    }

    //Write to a file
    private void writeToFile(String path, String line, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> findAll() {
        List<String> lines = readFromFile(path);
        List<User> users = new ArrayList<>();
        for(String line:lines){
            users.add(parseUser(line));
        }
        return users;
    }

    public User findByID(int userID){
     List<User> users=findAll();
     for(User u:users) {
         if (u.getUserID() == userID) {
             return u;
         }
     }
     return null;
    }

    abstract String formatUser(User student);

    //Parse text -> User
    abstract User parseUser(String line);

    private List<String> readFromFile(String path){
        List<String> lines=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }
}
