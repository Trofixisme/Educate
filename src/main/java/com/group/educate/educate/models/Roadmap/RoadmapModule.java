//Created by Ziad on 30/10/2025

package com.group.educate.educate.models.Roadmap;

import com.group.educate.educate.models.Roadmap.Skills.Skills;

import java.util.ArrayList;
import java.util.List;


public class RoadmapModule {

    private static int counter=0;
    private final int moduleId;
    private String name;
    private String description;

    private ArrayList<Skills> skills = new ArrayList<>();

    public RoadmapModule(String name, String description) {
        this.moduleId=++counter;
        this.name = name;
        this.description = description;
    }

    public RoadmapModule(String name, String description, Skills[] skills) {
        this(name, description);
        addSkills(skills);
    }

    public int getModuleID() {
        return moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSkills(Skills... skills) {
        this.skills.addAll(List.of(skills));
    }
}
