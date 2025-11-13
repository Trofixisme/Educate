//Created by Ziad on 30/10/2025

package com.group.educate.educate.Model.Roadmap;

import com.group.educate.educate.Model.Roadmap.Skills.Skills;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class RoadmapModule {

    private UUID ID = UUID.randomUUID();
    private String name;
    private String description;

    private ArrayList<Skills> skills = new ArrayList<>();

    public RoadmapModule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoadmapModule(String name, String description, Skills[] skills) {
        this(name, description);
        addSkills(skills);
    }

    public RoadmapModule(String name, Skills[] skills) {
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
        }
        else throw new RuntimeException("new name cannot be blank or null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.isBlank()) this.description = description;
        else throw new RuntimeException("new description cannot be blank or null");
    }

    public void addSkills(Skills... skills) {
        this.skills.addAll(List.of(skills));
    }
}
