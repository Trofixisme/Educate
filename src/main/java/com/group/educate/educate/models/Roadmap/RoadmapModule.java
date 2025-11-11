//Created by Ziad on 30/10/2025

package com.group.educate.educate.models.Roadmap;

import com.group.educate.educate.models.Roadmap.Skills.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoadmapModule {

    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private int sequenceNumber;

    private Roadmap parentRoadmap = null;
    private ArrayList<Skills> skills = new ArrayList<>();

    public RoadmapModule(String name, String description, int sequenceNumber) {
        this.name = name;
        this.description = description;
        this.sequenceNumber = sequenceNumber;
    }

    public RoadmapModule(String name, String description, Roadmap parentRoadmap , int sequenceNumber) {
        this(name, description, sequenceNumber);
        this.parentRoadmap = parentRoadmap;
    }

    public RoadmapModule(String name, String description, Roadmap parentRoadmap, int sequenceNumber, Skills[] skills) {
        this(name, description, sequenceNumber);
        this.parentRoadmap = parentRoadmap;
        addSkills(skills);
    }

    public RoadmapModule(String name, String description, int sequenceNumber, Skills[] skills) {
        this(name, description, null, sequenceNumber, skills);
    }

    public String getModuleID() {
        return id.toString();
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

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setNewParent(Roadmap parentRoadmap) {
        this.parentRoadmap = parentRoadmap;
    }

    public void addSkills(Skills... skills) {
        this.skills.addAll(List.of(skills));
    }
}
