//Created by Ziad on 30/10/2025

package com.group.educate.Model.Roadmap;

import com.group.educate.Model.Roadmap.Skill.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class RoadmapModule {

    private final UUID ID = UUID.randomUUID();
    private String name;
    private String description;

    private final ArrayList<Skill> skills = new ArrayList<>();

    public RoadmapModule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoadmapModule(String name, String description, Skill... skills) {
        this(name, description);
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
}
