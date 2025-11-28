package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;
import com.group.InternMap.Repo.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends UserService implements FilePaths {
    //todo:view all users
    //todo:delete users
    //todo:create roadmaps
    //todo:delete roadmaps
    //todo:update roadmaps
    //todo:view all job postings
    //todo:delete job posting
    //I need a switch case to delete to determine which user is to be deleted

    private final BaseRepository<User> userRepo = new BaseRepository<>(User.class, userPath);
    private final BaseRepository<Roadmap> roadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);
    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);

    //View all users
    public List<User> viewAllUsers() throws Exception {
        return userRepo.findAll();
    }

    public List<Student> findAllStudents() throws Exception {
        return repo.findAll().stream()
                .filter(u -> u.getRole() == UserRole.STUDENT)
                .map(u -> (Student) u)
                .toList();
    }

    public List<Recruiter> findAllRecruiters() throws Exception {
        return repo.findAll().stream()
                .filter(u -> u.getRole() == UserRole.RECRUITER)
                .map(u -> (Recruiter) u)
                .toList();
    }
    //Deleting users

    //Doesn't matter which one since email is unique
    public void deleteUser(String email) throws Exception {

        List<User> users = userRepo.findAll();
        //The u is the user, the method basically does the job of loop
        //but safer and without causing run time error
        users.removeIf(u -> u.getEmail().equals(email));
        userRepo.saveAll(users);
    }

    public void addRoadmap(Roadmap roadmap) throws Exception {
        List<Roadmap> roadmaps= roadmapRepo.findAll();
        roadmaps.add(roadmap);
        roadmapRepo.saveAll(roadmaps);
    }

    public void removeRoadmap(Roadmap roadmap) throws Exception {
        List<Roadmap> roadmaps= roadmapRepo.findAll();
        roadmaps.remove(roadmap);
        roadmapRepo.saveAll(roadmaps);
    }

    //View all job posting
    public List<JobPosting> viewAllJobPosting() throws Exception {
        return jobRepo.findAll();
    }

    public void removeJobPosting(JobPosting jobPosting) throws Exception {
        List<JobPosting> jobPostings = jobRepo.findAll();
        jobPostings.remove(jobPosting);
        jobRepo.saveAll(jobPostings);
    }

}
