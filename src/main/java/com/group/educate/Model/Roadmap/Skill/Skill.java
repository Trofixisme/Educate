//Created by Ziad on 30/10/2025

package com.group.educate.Model.Roadmap.Skill;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Skill {

    private static int counter=0;
    private final int skillsId;
    private String name;
    private final ArrayList<URL> resourceLinks  = new ArrayList<>();
    //description
    private String description;

    public Skill(String name) {
        skillsId=++counter;
        this.name = name;
    }
    public int getSkillsId() {
        return skillsId;
    }


    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void addURLS(URL... resourceLinks) {
        this.resourceLinks.addAll(List.of(resourceLinks));
    }

    public static int getCounter() {
        return counter;
    }

    public ArrayList<URL> getResourceLinks() {
        return resourceLinks;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
