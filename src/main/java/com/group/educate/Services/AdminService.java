package com.group.educate.Services;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.Roadmap.Roadmap;
import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends UserService{
    //todo:view all users
    //todo:delete users
    //todo:create roadmaps
    //todo:delete roadmaps
    //todo:update roadmaps
    //todo:view all job postings
    //todo:delete job posting
    //i need a switch case to delete which user exactly

    private static final String fileNameUser = "data/users.txt";
    private static final String fileNameRoadmap = "data/roadmaps.txt";
    private static final String fileNameJob = "data/jobs.txt";
    private final BaseRepository<User> userRepo = new BaseRepository<>(User.class, fileNameUser);
    private final BaseRepository<Roadmap> roadmapRepo = new BaseRepository<>(Roadmap.class, fileNameRoadmap);
    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, fileNameJob);

    // view all users
    public List<User> viewAllUser() throws Exception {
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
    //deleting users

// doesnt matter which one since email is unique
    public void deleteUser(String email) throws Exception {
        List<User> users= userRepo.findAll();
        // the u is the user, the method basically does the job of loop
        //but safer and without causing run time error
        users.removeIf(u->u.getEmail().equals(email));
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
        // view all job posting
        public List<JobPosting> viewAllJobPosting() throws Exception {
        return jobRepo.findAll();
        }

    public void removeJobPosting(JobPosting jobPosting) throws Exception {
        List<JobPosting> jobPostings= jobRepo.findAll();
        jobPostings.remove(jobPosting);
        jobRepo.saveAll(jobPostings);
    }

}
