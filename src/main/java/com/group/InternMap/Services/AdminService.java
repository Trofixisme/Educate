package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
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
    //View all users
    public List<User> viewAllUsers() throws Exception {
        return RepositoryAccessors.allUsers;
    }

    public List<Student> findAllStudents() throws Exception {
        return RepositoryAccessors.allUsers.stream()
                .filter(u -> u.getRole() == UserRole.STUDENT)
                .map(u -> (Student) u)
                .toList();
    }

    public List<Recruiter> findAllRecruiters() throws Exception {
        return RepositoryAccessors.allUsers.stream()
                .filter(u -> u.getRole() == UserRole.RECRUITER)
                .map(u -> (Recruiter) u)
                .toList();
    }
    //Deleting users

    //Doesn't matter which one since email is unique
    public void deleteUser(String email) throws Exception {

        List<User> users = RepositoryAccessors.allUsers;
        //The u is the user, the method basically does the job of loop
        //but safer and without causing run time error
        users.removeIf(u -> u.getEmail().equals(email));
    }

    public void addRoadmap(Roadmap roadmap) throws Exception {
        List<Roadmap> roadmaps= RepositoryAccessors.allRoadmaps;
        roadmaps.add(roadmap);
    }

    public void removeRoadmap(Roadmap roadmap) throws Exception {
        RepositoryAccessors.allRoadmaps.remove(roadmap);
    }

    //View all job posting
    public List<JobPosting> viewAllJobPosting() throws Exception {
        return RepositoryAccessors.allJobPostings;
    }

    public void removeJobPosting(JobPosting jobPosting) throws Exception {
        RepositoryAccessors.allJobPostings.remove(jobPosting);
    }

}
