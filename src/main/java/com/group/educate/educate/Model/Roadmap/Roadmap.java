//Created by Ziad on 30/10/2025

package com.group.educate.educate.Model.Roadmap;

import ch.qos.logback.core.pattern.color.ANSIConstants;

import java.util.*;

public final class Roadmap {

    private UUID ID = UUID.randomUUID();
    private String name;
    private ArrayList<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap(String name, RoadmapModule... modules) {
        this.name = name;

        if (modules != null)
            addModules(modules);

    }

    @SuppressWarnings({"all"})
    public Roadmap(String name) {
        this(name, null);
    }

    private String getName() {
        return name;

    }

    public void setName(String newName) {
        name = newName;
    }

    public String getRoadmapID() {
        return ID.toString();
    }

    void addModule(RoadmapModule module) {
        addModules(module);
    }

    void addModules(RoadmapModule... modules) {
        roadmapModules.addAll(List.of(modules));
    }

    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder(String.format("\u001B[31mRoadmap: \u001B[0m %s\n", name));
        returnValue.append("\u001B[34m Modules {\u001B[0m\n");

        if (!roadmapModules.isEmpty()) {
            for (RoadmapModule i : roadmapModules) {
                returnValue.append("  - ").append(i.getName()).append(": ").append(i.getDescription()).append("\n");
            }
        } else {
            returnValue.append("  --Empty--\n");
        }
        returnValue.append("\u001B[34m } \u001B[0m");
        return returnValue.toString();
    }

    private static class RoadmapTest {

        static void main(String[] args) {
            Roadmap roadmap = new Roadmap("Hello");
            roadmap.addModules(new RoadmapModule("Hello", "Hello"), new RoadmapModule("Henlo", "Benlo"), new RoadmapModule("Macarana", "Genesis"));
            System.out.println(roadmap);
        }
    }
}
