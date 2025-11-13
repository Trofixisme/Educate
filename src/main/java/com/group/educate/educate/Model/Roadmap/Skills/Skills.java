//Created by Ziad on 30/10/2025

package com.group.educate.educate.Model.Roadmap.Skills;


public class Skills {

    private static int counter=0;
    private final int skillsId;
    private String name;

    public Skills(String name) {
        skillsId=++counter;
        this.name = name;
    }
    public int getSkillsId() {
        return skillsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}
