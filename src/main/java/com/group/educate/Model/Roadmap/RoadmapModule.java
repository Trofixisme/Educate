//Created by Ziad on 30/10/2025

package com.group.educate.Model.Roadmap;

import com.group.educate.Model.Roadmap.Skill.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoadmapModule implements Serializable {

    private final UUID ID;
    private String name;
    private String description;

    private final ArrayList<Skill> skills = new ArrayList<>();

    //TODO: add two constructors for the repositories

    public RoadmapModule(String name, String description) {
        ID = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public RoadmapModule(String name, String description, Skill... skills) {
        ID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        addSkills(skills);
    }

    public RoadmapModule(String name, Skill... skills) {
        this(name, "Nothing to show.", skills);
    }

    public RoadmapModule(String name) {
        this(name, "Nothing to show.");
    }

    public String getModuleID() {
        return ID.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            name = name.strip();
            this.name = name;
        } else throw new RuntimeException("new name cannot be blank or null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.isBlank()) this.description = description;
        else throw new RuntimeException("new description cannot be blank or null");
    }

    public void addSkills(Skill... skills) {
        this.skills.addAll(List.of(skills));
    }

    ArrayList<Skill> getAllSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return ID + "|" +
                name + "|" +
                description + "|" +
                skills + "|";
    }
}
