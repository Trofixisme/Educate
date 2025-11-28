//Created by Ziad on 30/10/2025

package com.group.InternMap.Model.Roadmap.Skill;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Skill implements Serializable {

    private final UUID skillsId;

    private String name;
    private final ArrayList<URL> resourceLinks = new ArrayList<>();

    private String description;

    public Skill(String name, String description, List<URL> links) {
        this.skillsId = UUID.randomUUID();
        this.name = name;
        this.description = description;

        if (links != null) {
            this.resourceLinks.addAll(links);
        }
    }

    public Skill(String skillsId, String name, String description, List<URL> links) {
        this.skillsId = UUID.fromString(skillsId);
        this.name = name;
        this.description = description;

        if (links != null) {
            this.resourceLinks.addAll(links);
        }
    }

    public String getSkillsId() {
        return skillsId.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void addURLs(URL... resourceLinks) {
        this.resourceLinks.addAll(List.of(resourceLinks));
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

    @Override
    public String toString() {
        return getSkillsId() + '|'
                + name + '|'
                + resourceLinks + '|'
                + description + '|';
    }
}
