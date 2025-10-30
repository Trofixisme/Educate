//Created by Ziad on 30/10/2025
//NOTE: Codebase is incomplete

package com.group.educate.educate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuidedProject {

    private UUID id = UUID.randomUUID();
    private String projectTitle;
    private String description;
    private ArrayList<String> requirements = new ArrayList<>();

    public GuidedProject(String projectTitle, String description) {
        this.projectTitle = projectTitle;
        this.description = description;
    }

    public GuidedProject(String projectTitle, String description, String... requirements) {
        this(projectTitle, description);
        addRequirements(requirements);
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public void addRequirements(String... newRequirements) {
        requirements.addAll(List.of(newRequirements));
    }

    public void EraseRequirements() {
        requirements = new ArrayList<>();
    }

    public String getRequirement(int index) {
        return requirements.get(index);
    }
}
