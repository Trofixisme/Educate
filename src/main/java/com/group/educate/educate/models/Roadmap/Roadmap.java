//Created by Ziad on 30/10/2025

package com.group.educate.educate.models.Roadmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Roadmap {

    private UUID id = UUID.randomUUID();
    private String name;
    private ArrayList<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap(String name, RoadmapModule... modules) {
        this.name = name;
        addModules(modules);
    }

    private String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getRoadmapID() {
        return id.toString();
    }

    public void addModule(RoadmapModule module) {
        addModules(module);
    }

    public void addModules(RoadmapModule... modules) {
        for (RoadmapModule module: modules) {
            module.setNewParent(this);
        }
        roadmapModules.addAll(List.of(modules));
    }
}
