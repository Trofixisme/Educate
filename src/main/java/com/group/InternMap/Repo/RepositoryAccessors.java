package com.group.InternMap.Repo;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Services.FilePaths;
import org.flywaydb.core.internal.util.JsonUtils;

import java.util.ArrayList;

public final class RepositoryAccessors {

    public static final ArrayList<User> allUsers = (ArrayList<User>) (new BaseRepository<>(User.class, FilePaths.userPath)).findAll();
    public static final ArrayList<Company> allCompanies = (ArrayList<Company>) (new BaseRepository<>(Company.class, FilePaths.userPath)).findAll();
    public static final ArrayList<Roadmap> allRoadmaps = (ArrayList<Roadmap>) (new BaseRepository<>(Roadmap.class, FilePaths.userPath)).findAll();
    public static final ArrayList<JobPosting> allJobPostings = (ArrayList<JobPosting>) (new BaseRepository<>(JobPosting.class, FilePaths.userPath)).findAll();
    public static final ArrayList<Application> allApplications = (ArrayList<Application>) (new BaseRepository<>(Application.class, FilePaths.userPath)).findAll();

    public static void saveAll() {
        try {
            new BaseRepository<>(User.class, FilePaths.userPath).saveAll(allUsers);
            new BaseRepository<>(Company.class, FilePaths.companyPath).saveAll(allCompanies);
            new BaseRepository<>(Roadmap.class, FilePaths.roadmapPath).saveAll(allRoadmaps);
            new BaseRepository<>(JobPosting.class, FilePaths.jobPostingPath).saveAll(allJobPostings);
            new BaseRepository<>(Application.class, FilePaths.applicationPath).saveAll(allApplications);
            System.out.println("\u001B[32mSaved all repositories successfully.");
            Runtime.getRuntime().halt(0);
        } catch (Exception e) {
            Runtime.getRuntime().halt(-1);
            System.out.println("\u001B[31mFailed to save one or more of the repositories: ");
            System.out.println(e.getMessage());
        } finally {
            System.out.println("\u001B[34mInternMap Exited\u001B[0m");
        }
    }
}
