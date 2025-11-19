package com.group.educate.Repo;

import com.group.educate.Model.User.User;
import com.group.educate.Model.Roadmap.Skill.Skill;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.flywaydb.core.internal.util.FileUtils.writeToFile;

public class SkillRepository extends BaseRepository {

    public SkillRepository() {
        super("data/Skill.txt");
    }

    //TODO: FIX
    @Override
    Object parseObject(String line) {
        String[] parts = line.split("\\|");
//        return new Skill(parts[0], parts[1], Arrays.asList(parts[2].split(",")));
        return null;
    }
}