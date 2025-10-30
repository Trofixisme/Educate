//Created by Ziad on 30/10/2025

package com.group.educate.educate.Roadmap.Skills;

import java.util.UUID;

public class Skills {

    private UUID id = UUID.randomUUID();
    private String name;

    public Skills(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}
