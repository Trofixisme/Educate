package com.group.educate.Repo;

import com.group.educate.Model.User.User;
import com.group.educate.Model.Roadmap.Skill.Skill;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.flywaydb.core.internal.util.FileUtils.writeToFile;

public class SkillRepository {
    private final String path="data/Skill.txt";
    public SkillRepository(){
        File file=new File("data");
        if(!file.exists()) file.mkdirs();
    }
    public void save(Skill skill){
        String line=formatUser(skill);
        writeToFile(path,line,true);
    }
    private String formatUser(Skill skill) {
        return   skill.getSkillsId() + "|" +
                 skill.getName()+ "|" +
                 skill.getDescription()+ "|" +
                 skill.getResourceLinks() + "|" ;
    }
    // Write to file
    private void writeToFile(String path, String line, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Skill> findAll() {
        List<String> lines = readFromFile(path);
        List<Skill> skills = new ArrayList<>();
        for(String line:lines){
            skills.add(parseSkill(line));
        }
        return skills;
    }
    //public User findById(int userId){
      //  List<User> users=findAll();
        //for(User u:users) {
          //  if (u.getUserId() == userId) {
            //    return u;
            //}
        //}
        //return null;
    //}
    public Skill findById(int skillId){
        List<Skill> skills=findAll();
        for(Skill s:skills) {
            if (s.getSkillsId() == skillId) {
                return s;
            }
        }
        return null;
    }
    Skill parseSkill(String line){
        String[] parts=line.split("\\|");
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String description = parts[2].trim();
        URL url = null;
        // convert String → URL object
        try {
            url = new URL(parts[3].trim());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<URL> urls = new ArrayList<>();
        return new Skill(id,name,description,urls);
    }
    public List<String> readFromFile(String path){
        List<String> lines=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }
    public static void main(String[] args) {
        SkillRepository sr = new SkillRepository();
        ArrayList<URL> urls = new ArrayList<>();

        try {
            urls.add(new URL("https://google.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Skill s = new Skill(1, "java basics", "learn java", urls);
        sr.save(s);
        sr.findAll();
    }
    }

